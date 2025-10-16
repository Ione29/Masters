select last_name, round(months_between(sysdate, hire_date)) "MONTHS_WORKED"
from employees
order by months_worked;
