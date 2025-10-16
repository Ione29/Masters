SELECT department_id 
FROM departments MINUS 
SELECT department_id 
FROM employees 
WHERE job_id 
LIKE 'ST_CLERK';