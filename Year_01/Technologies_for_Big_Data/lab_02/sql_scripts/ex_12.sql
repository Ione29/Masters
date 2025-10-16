SELECT last_name, first_name 
FROM employees 
WHERE job_id 
LIKE 'SA_REP' 
INTERSECT SELECT e.last_name, e.first_name 
FROM employees e 
JOIN departments d 
ON (e.department_id = d.department_id) 
WHERE d.department_id 
LIKE 80;