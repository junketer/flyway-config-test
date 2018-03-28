CREATE PROCEDURE OAG.oagdb2load (IN p_schema_table VARCHAR(200),
IN p_column_list VARCHAR(32672),
IN p_select_clause VARCHAR(32672),
OUT p_message_txt VARCHAR(2048))


LANGUAGE SQL




P1: BEGIN


DECLARE v_cursor_sql      VARCHAR(32672) DEFAULT '';--
DECLARE v_load_sql        VARCHAR(32672) DEFAULT '';--
DECLARE v_sql_stmt VARCHAR(1000);--
DECLARE v_runstats_command VARCHAR(256);--


DECLARE db2load_VERSION_NUMBER	        INTEGER	DEFAULT 1;--
DECLARE db2load_SQLCODE	                INTEGER;--
DECLARE db2load_SQLMESSAGE              VARCHAR(2048);--
DECLARE db2load_ROWS_READ       	BIGINT;--
DECLARE db2load_ROWS_SKIPPED	        BIGINT;--
DECLARE db2load_ROWS_LOADED	        BIGINT;--
DECLARE db2load_ROWS_REJECTED	        BIGINT;--
DECLARE db2load_ROWS_DELETED		BIGINT;--
DECLARE db2load_ROWS_COMMITTED		BIGINT;--
DECLARE db2load_ROWS_PART_READ		BIGINT;--
DECLARE db2load_ROWS_PART_REJECTED	BIGINT;--
DECLARE db2load_ROWS_PART_PARTITIONED	BIGINT;--
DECLARE db2load_MPP_LOAD_SUMMARY        VARCHAR(32672);--


SET v_cursor_sql = 'DECLARE loadcursor CURSOR WITH HOLD FOR ' || p_select_clause || ' FOR READ ONLY';--




IF LTRIM(RTRIM(p_column_list)) = '' THEN
SET v_load_sql = 'LOAD FROM loadcursor OF CURSOR MESSAGES ' ||
'/home/db2inst1/logs/oag/db2load/msgs.msg' ||
' INSERT INTO ' || p_schema_table || ' NONRECOVERABLE';--
ELSE
SET v_load_sql = 'LOAD FROM loadcursor OF CURSOR MESSAGES '||
'/home/db2inst1/logs/oag/db2load/msgs.msg' ||
' INSERT INTO ' || p_schema_table || '(' || p_column_list || ') NONRECOVERABLE';--
END IF;--


CALL SYSPROC.DB2LOAD (db2load_VERSION_NUMBER,
v_cursor_sql,
v_load_sql,
db2load_SQLCODE,
db2load_SQLMESSAGE,
db2load_ROWS_READ,
db2load_ROWS_SKIPPED,
db2load_ROWS_LOADED,
db2load_ROWS_REJECTED,
db2load_ROWS_DELETED,
db2load_ROWS_COMMITTED,
db2load_ROWS_PART_READ,
db2load_ROWS_PART_REJECTED,
db2load_ROWS_PART_PARTITIONED,
db2load_MPP_LOAD_SUMMARY);--


SET v_sql_stmt = 'SET INTEGRITY FOR ' || p_schema_table || ' ALL IMMEDIATE UNCHECKED';--
EXECUTE IMMEDIATE v_sql_stmt;--


IF db2load_SQLCODE <> 0    OR    (db2load_ROWS_LOADED = 0 AND db2load_ROWS_READ > 0) THEN
SET p_message_txt = p_schema_table || ': ' || ' SQLCODE = ' || RTRIM(CHAR(db2load_SQLCODE)) || COALESCE(db2load_SQLMESSAGE,'') || ' ROWS LOADED = ' || RTRIM(CHAR(db2load_ROWS_LOADED));--
END IF;--


SET v_runstats_command = 'RUNSTATS ON TABLE ' ||
p_schema_table ||
' ON ALL COLUMNS WITH DISTRIBUTION ON ALL COLUMNS AND INDEXES ALL ALLOW WRITE ACCESS';--
CALL ADMIN_CMD(v_runstats_command);--

END P1;

