import matplotlib.pyplot as plt
from matplotlib.patches import Circle

def draw_circle(center=(0, 0), radius=1.0, facecolor='none', edgecolor='C0', linewidth=2):
    fig, ax = plt.subplots()
    circ = Circle(center, radius, facecolor=facecolor, edgecolor=edgecolor, linewidth=linewidth)
    ax.add_patch(circ)
    ax.set_aspect('equal', 'box')
    pad = radius * 0.2
    ax.set_xlim(center[0] - radius - pad, center[0] + radius + pad)
    ax.set_ylim(center[1] - radius - pad, center[1] + radius + pad)
    ax.grid(True)
    plt.show()

if __name__ == '__main__':
    draw_circle(center=(0, 0), radius=1.5, facecolor='none', edgecolor='red', linewidth=2)
