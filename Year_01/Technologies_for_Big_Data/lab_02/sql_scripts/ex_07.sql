SELECT e.last_name "EMPLOYEE", e.employee_id "EMP#", m.last_name "MANAGER", m.manager_id "MGR#" 
FROM employees e 
LEFT OUTER JOIN employees m 
ON(e.manager_id = m.employee_id);