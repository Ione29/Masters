SELECT last_name || ' earns ' || TO_CHAR(salary, 'fm$99,999') || ' monthly but wants ' || TO_CHAR(salary * 3, 'fm$99,999') || '.' "Dream Salaries" 
FROM employees;