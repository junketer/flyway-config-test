CREATE FUNCTION OAG.dateformat ( p_input_date DATE, p_date_format VARCHAR(30)
)
RETURNS VARCHAR(20)
LANGUAGE SQL
CONTAINS SQL
DETERMINISTIC
NO EXTERNAL ACTION
--
-- Reformats a Date value into a text string determined by the p_date_format parameter
--
-- p_input_date
--      The date value to be reformatted
--
-- p_date_format
--      One of several possible values:
--		DDMMMYY
--		DDMMYY
--		YYMMDD
--		CCYYMMDD / YYYYMMDD
--		....
--	(List to be extended ?)
--
-- Revision History
-- Date		Name		Detail
-- ----------   -----------	-----------------------------------
-- 01-04-2008	B Mackenzie	UDF created
-- 01-04-2008	S Corcoran	Tweaked to use CASE stmt (allow future expansion)
-- 13-05-2008	S Corcoran	Added additional format codes ccyymmdd, yyyymmdd
-- 16-02-2009	S Corcoran	Added additional format codes dd/mm/ccyy,  dd/mm/yyyy, dd/mm/yy
--

UDF: BEGIN ATOMIC
RETURN
    CASE UPPER(p_date_format)
	WHEN 'DDMMMYY' THEN
		RIGHT(DIGITS(DAY(p_input_date)),2) ||
		LEFT(UPPER(MONTHNAME(p_input_date)),3) ||
		RIGHT(DIGITS(YEAR(p_input_date)),2)
	WHEN 'DDMMYY' THEN
		RIGHT(DIGITS(DAY(p_input_date)),2) ||
		RIGHT(DIGITS(MONTH(p_input_date)),2) ||
		RIGHT(DIGITS(YEAR(p_input_date)),2)
	WHEN 'YYMMDD' THEN
		RIGHT(DIGITS(YEAR(p_input_date)),2) ||
		RIGHT(DIGITS(MONTH(p_input_date)),2) ||
		RIGHT(DIGITS(DAY(p_input_date)),2)
	WHEN 'CCYYMMDD' THEN
		RIGHT(DIGITS(YEAR(p_input_date)),4) ||
		RIGHT(DIGITS(MONTH(p_input_date)),2) ||
		RIGHT(DIGITS(DAY(p_input_date)),2)
	WHEN 'YYYYMMDD' THEN
		RIGHT(DIGITS(YEAR(p_input_date)),4) ||
		RIGHT(DIGITS(MONTH(p_input_date)),2) ||
		RIGHT(DIGITS(DAY(p_input_date)),2)
	WHEN 'DD/MM/CCYY' THEN
		RIGHT(DIGITS(DAY(p_input_date)),2) || '/' ||
		RIGHT(DIGITS(MONTH(p_input_date)),2) || '/' ||
		RIGHT(DIGITS(YEAR(p_input_date)),4)
	WHEN 'DD/MM/YYYY' THEN
		RIGHT(DIGITS(DAY(p_input_date)),2) || '/' ||
		RIGHT(DIGITS(MONTH(p_input_date)),2) || '/' ||
		RIGHT(DIGITS(YEAR(p_input_date)),4)
	WHEN 'DD/MM/YY' THEN
		RIGHT(DIGITS(DAY(p_input_date)),2) || '/' ||
		RIGHT(DIGITS(MONTH(p_input_date)),2) || '/' ||
		RIGHT(DIGITS(YEAR(p_input_date)),2)
	ELSE -- Return DDMMMYY format anyway as a default!
		RIGHT(DIGITS(DAY(p_input_date)),2) ||
		LEFT(UPPER(MONTHNAME(p_input_date)),3) ||
		RIGHT(DIGITS(YEAR(p_input_date)),2)
    END;--
END UDF;
