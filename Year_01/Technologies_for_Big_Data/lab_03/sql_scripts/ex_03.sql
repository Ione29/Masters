select employee_id, last_name, department_id 
from employees
where department_id
in
(select department_id from departments where location_id 
in
(select location_id from locations where city like 'T%')
);