SELECT last_name, department_id, salary
FROM employees
where (salary, department_id)
in (select salary, department_id from employees where commission_pct is not null);