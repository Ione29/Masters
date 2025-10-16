select last_name || ', ' || rpad(' ', salary/1000, '*') as "EMPLOYEES_AND_THEIR_SALARIES"
from employees
order by salary desc;
