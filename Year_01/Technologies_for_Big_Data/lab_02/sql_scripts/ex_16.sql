CREATE OR REPLACE VIEW dept_80_view 
AS SELECT employee_id "EMPNO", last_name "EMPLOYEE", department_id "DEPTNO" 
FROM employees 
WHERE department_id = 80 
WITH CHECK OPTION CONSTRAINT emp_dept_80;