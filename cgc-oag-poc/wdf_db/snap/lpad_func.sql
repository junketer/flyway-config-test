CREATE FUNCTION OAG.lpad (p_string VARCHAR(4000), p_len INTEGER, p_padding
VARCHAR(4000))
RETURNS VARCHAR(4000)
LANGUAGE SQL
DETERMINISTIC
NO EXTERNAL ACTION
CONTAINS SQL
UDF: BEGIN ATOMIC
DECLARE v_pad_cnt INTEGER;--
DECLARE v_padding VARCHAR(4000);--
DECLARE v_string VARCHAR(4000);--
SET v_pad_cnt = ROUND(p_len / LENGTH(p_padding),0) + 1;--
SET v_padding = REPEAT( p_padding , v_pad_cnt );--
IF p_len < LENGTH(p_string) THEN
SET v_string = RIGHT(p_string, p_len);--
ELSE
SET v_string = SUBSTR ( v_padding, 1, (p_len - LENGTH(p_string)) ) || p_string;--
END IF;--
RETURN v_string;--
END UDF;

CREATE FUNCTION OAG.rpad (p_string VARCHAR(4000), p_len INTEGER, p_padding
VARCHAR(4000))
RETURNS VARCHAR(4000)
LANGUAGE SQL
DETERMINISTIC
NO EXTERNAL ACTION
CONTAINS SQL
UDF: BEGIN ATOMIC
DECLARE v_pad_cnt INTEGER;--
DECLARE v_padding VARCHAR(4000);--
DECLARE v_string VARCHAR(4000);--
SET v_pad_cnt = ROUND(p_len / LENGTH(p_padding),0) + 1;--
SET v_padding = REPEAT( p_padding , v_pad_cnt );--
IF p_len < LENGTH(p_string) THEN
SET v_string = LEFT(p_string, p_len);--
ELSE
SET v_string = p_string || SUBSTR ( v_padding, 1, (p_len - LENGTH(p_string)) );--
END IF;--
RETURN v_string;--
END UDF; 
