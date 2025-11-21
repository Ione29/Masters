import sys
import math
import random
from pathlib import Path
import pygame

# global Settings
WIDTH, HEIGHT = 800, 600
FPS = 60
ASSETS_DIR = Path(__file__).resolve().parent / "resources"
BEST_FILE = Path(__file__).resolve().parent / "best_lap.txt"

# iknitialize PyGame, draw the game window
pygame.init()
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Top-down Mini Racer")
# for lap times
clock = pygame.time.Clock()

pygame.mixer.init()
# helper function in order to load the sounds more convenient
def load_sound(name):
    p = ASSETS_DIR / "sounds" / name
    try:
        return pygame.mixer.Sound(str(p))
    except Exception:
        return None

engine_snd = load_sound("car_engine.wav")
checkpoint_snd = load_sound("checkpoint.wav")
finish_snd = load_sound("finish_line.wav")

# draw the track - AI helped here mostly
def create_track():
    surf = pygame.Surface((WIDTH, HEIGHT), pygame.SRCALPHA).convert_alpha()
    ASPHALT = (220, 220, 220, 255)
    
    margin = 80
    road_width = 70
    center_x = WIDTH // 2
    center_y = HEIGHT // 2
    
    # 1. Left vertical (start line here)
    pygame.draw.rect(surf, ASPHALT, 
                    (margin, margin, road_width, HEIGHT - 2*margin))
    
    # 2. Bottom horizontal
    pygame.draw.rect(surf, ASPHALT,
                    (margin, HEIGHT - margin - road_width, WIDTH - 2*margin, road_width))
    
    # 3. Right vertical (only bottom half - from center down)
    pygame.draw.rect(surf, ASPHALT,
                    (WIDTH - margin - road_width, center_y, road_width, HEIGHT//2 - margin))
    
    # 4. Horizontal connector (center to right)
    pygame.draw.rect(surf, ASPHALT,
                    (center_x, center_y, WIDTH//2 - margin, road_width))
    
    # 5. Vertical connector (top to center)
    pygame.draw.rect(surf, ASPHALT,
                    (center_x, margin, road_width, center_y - margin))
    
    # 6. Top horizontal (left side only - to the center)
    pygame.draw.rect(surf, ASPHALT,
                    (margin, margin, center_x - margin, road_width))
    
    # Fill the center intersection completely to avoid gaps
    pygame.draw.rect(surf, ASPHALT,
                    (center_x, center_y, road_width, road_width))
    
    # Draw Starting / Finish line
    start_line = pygame.Rect(margin + 8, center_y - 6, road_width - 16, 12)
    checker_size = 6
    for i in range(0, start_line.width, checker_size):
        for j in range(0, start_line.height, checker_size):
            # Alternate black and white in a checkerboard pattern
            if (i // checker_size + j // checker_size) % 2 == 0:
                color = (255, 255, 255)  # white
            else:
                color = (0, 0, 0)  # black
            pygame.draw.rect(surf, color, (start_line.x + i, start_line.y + j, checker_size, checker_size))
    
    # Checkpoint
    checkpoint_rect = pygame.Rect(WIDTH - margin - road_width + 8, center_y + road_width + 10, road_width - 16, 12)
    
    track_mask = pygame.mask.from_surface(surf)
    return surf, track_mask, start_line, checkpoint_rect

# function to draw the decorations - AI helped a bit 
def draw_decorations(screen, track_mask):
    random.seed(0)
    # draw trees, tree stubs and bushes
    for _ in range(120):
        # random position
        x = random.randint(20, WIDTH - 20)
        y = random.randint(20, HEIGHT - 20)
        # don't draw on track
        if track_mask.get_at((x, y)):
                continue
        # tree (simple circles) - slightly larger and more varied
        decoration_radius = random.randint(10, 22)
        trunk_h = max(3, decoration_radius // 3)
        # brown stubs
        pygame.draw.rect(screen, (101, 67, 33), (x - decoration_radius // 5, y + decoration_radius - trunk_h, max(3, decoration_radius // 3), trunk_h))
        # green foliage with slight color variation
        bush_color = (20 + random.randint(0, 30), 100 + random.randint(0, 40), 20 + random.randint(0, 30))
        pygame.draw.circle(screen, bush_color, (x, y), decoration_radius)
    # more rocks with variety
    for _ in range(40):
        # randomize the position of the rocks
        x = random.randint(10, WIDTH - 10)
        y = random.randint(10, HEIGHT - 10)
        # we do not generate decorations on the trrack
        if track_mask.get_at((x, y)):
                continue
        # how many rocks and each has a different color 
        decoration_radius = random.randint(5, 12)
        # different rock colors
        rock_color = random.randint(80, 120)
        pygame.draw.circle(screen, (rock_color, rock_color, rock_color), (x, y), decoration_radius)

# car class
class Car:
    def __init__(self, x, y, initial_angle=0):
        self.orig_surf = self.draw_car()
        self.surf = self.orig_surf
        self.rect = self.surf.get_rect(center=(x, y))
        self.pos = pygame.Vector2(x, y)
        self.angle = initial_angle
        self.speed = 0.0
        self.max_speed = 4.0
        self.accel = 0.08
        self.brake = 0.2
        self.turn_speed = 3.0
        self.last_off_time = 0

    def draw_car(color=(200, 20, 20)):
        try:
            # define car sizes
            CAR_W, CAR_H = 50, 50
            sprite_path = ASSETS_DIR / "sprites" / "car.png"
            car_img = pygame.image.load(str(sprite_path)).convert_alpha()
            # rotate the sprite in order for it to align correctly
            car_img = pygame.transform.rotate(car_img, -90)
            # car size
            car_img = pygame.transform.scale(car_img, (CAR_W, CAR_H))
            return car_img
        # just in case the car sprite does not work, load a basic model out of polys
        except Exception as e:
            CAR_W, CAR_H = 32, 16
            print(f"Could not load car sprite: {e}, using default")
            s = pygame.Surface((CAR_W, CAR_H), pygame.SRCALPHA)
            pygame.draw.polygon(s, color, [(0, CAR_H//2), (CAR_W-8, 0), (CAR_W, CAR_H//2), (CAR_W-8, CAR_H)])
            pygame.draw.rect(s, (0,0,0), (0,0,8, CAR_H), 1)
            return s

    # function definiing how the car is moving
    def update(self, dt, keys):
        # controls
        forward = keys[pygame.K_w] or keys[pygame.K_UP]
        backward = keys[pygame.K_s] or keys[pygame.K_DOWN]
        left = keys[pygame.K_a] or keys[pygame.K_LEFT]
        right = keys[pygame.K_d] or keys[pygame.K_RIGHT]

        if forward:
            self.speed = min(self.max_speed, self.speed + self.accel)
            if engine_snd:
                if not pygame.mixer.Channel(0).get_busy():
                    pygame.mixer.Channel(0).play(engine_snd, loops=-1)
        elif backward:
            self.speed = max(-self.max_speed/2, self.speed - self.brake)
            if engine_snd:
                if not pygame.mixer.Channel(0).get_busy():
                    pygame.mixer.Channel(0).play(engine_snd, loops=-1)
        # don't play athe engine sound if not going forwards or backwards
        else:
            if engine_snd:
                pygame.mixer.Channel(0).stop()

        # turning left / right
        if left:
            self.angle += self.turn_speed * (self.speed / self.max_speed)
        if right:
            self.angle -= self.turn_speed * (self.speed / self.max_speed)

        # movement of the sprite
        rad = math.radians(self.angle)
        direction = pygame.Vector2(math.cos(rad), -math.sin(rad))
        # update every game tick so it will behave mostly the same at any FPS speed 
        self.pos += direction * self.speed * dt * FPS 
        self.rect.center = (round(self.pos.x), round(self.pos.y))

        # rotate sprite with the car
        self.surf = pygame.transform.rotate(self.orig_surf, self.angle)
        self.rect = self.surf.get_rect(center=self.rect.center)

    def get_mask_and_offset(self):
        mask = pygame.mask.from_surface(self.surf)
        offset = (int(self.rect.left), int(self.rect.top))
        return mask, offset

# load the best time from the file
def load_best():
    try:
        with open(BEST_FILE, "r") as f:
            return float(f.read().strip())
    except Exception:
        return None

# save the best time in the best_lap.txt file
def save_best(v):
    try:
        with open(BEST_FILE, "w") as f:
            f.write(str(v))
    except Exception:
        pass

# game initialization

# initialize track data
track_surf, track_mask, start_line, checkpoint_rect = create_track()

# spawn car behind the finish line
car = Car(80 + 70 // 2, HEIGHT // 2 + 40, initial_angle=90)
# set up lap info
best_lap = load_best()
lap_start_time = None
current_lap = 1
last_crossed = False
last_on_checkpoint = False
# start with true so the first lap starts properly
passed_checkpoint = True
running = True

# UI
font = pygame.font.SysFont("consolas", 16)

def check_on_track(car):
    car_mask, offset = car.get_mask_and_offset()
    # offset relative to track surface: mask overlap expects (dx, dy) = (car_left - track_left, car_top - track_top)
    overlap = track_mask.overlap(car_mask, (offset[0], offset[1]))
    # overlap exists when car touches the road (white); returning True when on road
    return overlap is not None

# loop for running the game
while running:
    # game ticks
    dt = clock.tick(FPS) / 1000.0
    # close the game with the ESC key or ALT + F4 / the close button in the window decoration 
    for e in pygame.event.get():
        if e.type == pygame.QUIT:
            running = False
        elif e.type == pygame.KEYDOWN and e.key == pygame.K_ESCAPE:
            running = False

    # record the key that's pressed and move the car appropriately
    keys = pygame.key.get_pressed()
    car.update(dt, keys)

    # collision / off-road detection
    on_track = check_on_track(car)
    if not on_track:
        # massively slow down the car if going off-track
        car.speed *= 0.8

    # lap detection using checkpoint + start/finish gate
    cx, cy = car.pos.x, car.pos.y
    
    # detect when the car goes over the checkpoint
    # I implemented this so someone would have to actually do the laps
    on_checkpoint = checkpoint_rect.collidepoint(cx, cy)
    if on_checkpoint and not last_on_checkpoint:  # edge trigger
        passed_checkpoint = True
        if checkpoint_snd:
            checkpoint_snd.play()
    last_on_checkpoint = on_checkpoint
    
    # detect when the car goes over the finish line 
    crossed = start_line.collidepoint(cx, cy)
    # use both crossed and last_crossed so the finish line would not be triggered 
    # every game tick where the car is going over the finish line
    crossed_now = crossed and not last_crossed
    if crossed_now:
        if car.speed > 0 and passed_checkpoint:
            now = pygame.time.get_ticks() / 1000.0
            if lap_start_time is None:
                # First lap - just start the timer
                lap_start_time = now
            else:
                # Subsequent laps - record time
                lap_time = now - lap_start_time
                lap_start_time = now
                current_lap += 1
                if best_lap is None or lap_time < best_lap:
                    best_lap = lap_time
                    save_best(best_lap)
                if finish_snd:
                    finish_snd.play()
            # reset the checkpoint every lap
            passed_checkpoint = False
    last_crossed = crossed

    # actually draw the track
    screen.fill((34, 139, 34))  # fill with grass background
    draw_decorations(screen, track_mask)  # decorations layer over background layer
    screen.blit(track_surf, (0, 0))  # track layer on top of the decorations
    screen.blit(car.surf, car.rect.topleft) # the car goes on top of everything else
    
    # draw checkpoint (green when passed, red otherwise)
    pygame.draw.rect(screen, (80,200,80) if passed_checkpoint else (200,80,80), checkpoint_rect)

    # HUD

    # set HUD info
    hud_lines = []
    hud_lines.append(f"Lap: {current_lap}")
    if lap_start_time is not None:
        current_time = pygame.time.get_ticks() / 1000.0 - lap_start_time
        hud_lines.append(f"Current lap: {current_time:.2f}s")
    hud_lines.append(f"Best lap: {best_lap:.2f}s" if best_lap else "Best lap: --")
    hud_lines.append(f"Current speed: {car.speed:.2f}")
    hud_lines.append("")
    hud_lines.append("Controls:")
    hud_lines.append("WASD/Arrows - Move")
    hud_lines.append("ESC - Quit Game")

    # set HUD size
    line_height = 18
    padding = 8
    panel_width = 250
    panel_height = len(hud_lines) * line_height + padding * 2
    
    # HUD position
    hud_x = WIDTH - panel_width - 8
    hud_y = 8
    
    # HUD backgtround
    hud_surface = pygame.Surface((panel_width, panel_height), pygame.SRCALPHA)
    hud_surface.fill((0, 0, 0, 180))  # black with 70% opacity
    screen.blit(hud_surface, (hud_x, hud_y))
    
    # prepare the HUD info
    y = hud_y + padding
    for line in hud_lines:
        surf = font.render(line, True, (255, 255, 255))
        screen.blit(surf, (hud_x + padding, y))
        y += line_height

    # constantly redraw the HUD info
    pygame.display.flip()

pygame.quit()
sys.exit()