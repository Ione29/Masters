SELECT e.last_name, e.department_id, e.job_id 
FROM employees e 
JOIN departments d 
ON(e.department_id = d.department_id) 
WHERE location_id 
LIKE '1700';