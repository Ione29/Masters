SELECT job_id, COUNT(job_id) 
COUNTER FROM employees 
GROUP BY job_id;