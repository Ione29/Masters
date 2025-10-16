select last_name, trunc((sysdate-hire_date)/7) as "TENURE"
from employees
where department_id = 90
order by tenure desc;
