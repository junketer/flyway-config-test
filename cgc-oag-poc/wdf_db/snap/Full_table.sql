--
-- SNAPTPLT DATABASE - VERSION 14.0
--
-- table SCRIPT
--
-- ================================================================
-- Script generated via ERwin - Fri Sep 11 10:19:20 2009 - by SCORCORA.
-- From ModelMart://mmart/PhysicalRelease/Snapshot Version 14.0.              
-- ================================================================
--
--

SET CURRENT SCHEMA = 'SNAPSHOT';


CREATE TABLE snap_abbreviation (
       abbreviation_short_name VARCHAR(100) NOT NULL,
       abbreviation_long_name VARCHAR(100) NOT NULL,
       abbreviation_long_odi BIGINT,
       abbreviation_short_odi BIGINT
)
	 IN REF
;


ALTER TABLE snap_abbreviation 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkabbreviation ON snap_abbreviation
(
       abbreviation_short_name        ASC,
       abbreviation_long_name         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_abbreviation
       ADD CONSTRAINT xpkabbreviation PRIMARY KEY (
              abbreviation_short_name, abbreviation_long_name);


CREATE TABLE snap_acft_body_type (
       acft_body_type_cd    CHAR(1) NOT NULL,
       acft_body_type_desc  VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_acft_body_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkaircraft_body_t ON snap_acft_body_type
(
       acft_body_type_cd              ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_acft_body_type
       ADD CONSTRAINT xpkaircraft_body_t PRIMARY KEY (
              acft_body_type_cd);


CREATE TABLE snap_bank_closure (
       feature_id           BIGINT NOT NULL,
       bank_closure_cd      CHAR(1) NOT NULL,
       bank_closure_reason_odi BIGINT,
       bank_closure_reason_desc VARCHAR(255) NOT NULL,
       effective_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       effective_end_date   DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01')
)
	 IN REF
;


ALTER TABLE snap_bank_closure 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkbank_closure ON snap_bank_closure
(
       feature_id                     ASC,
       bank_closure_cd                ASC,
       effective_start_date           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_bank_closure
       ADD CONSTRAINT xpkbank_closure PRIMARY KEY (feature_id, 
              bank_closure_cd, effective_start_date);


CREATE TABLE snap_bank_closure_code (
       bank_closure_cd      CHAR(1) NOT NULL,
       bank_closure_reason_desc VARCHAR(255) NOT NULL,
       bank_closure_reason_odi BIGINT
)
	 IN REF
;


ALTER TABLE snap_bank_closure_code 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKbank_closure_cd ON snap_bank_closure_code
(
       bank_closure_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_bank_closure_code
       ADD CONSTRAINT XPKbank_closure_cd PRIMARY KEY (bank_closure_cd);


CREATE TABLE snap_bilateral_agreement (
       optional_element_num INTEGER NOT NULL,
       carrier_id           BIGINT NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_bilateral_agreement 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkbilateral_agree ON snap_bilateral_agreement
(
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_bilateral_agreement
       ADD CONSTRAINT xpkbilateral_agree PRIMARY KEY (carrier_id);


CREATE TABLE snap_boardpoint_offpoint (
       boardpoint_offpoint_cd VARCHAR(2) NOT NULL DEFAULT 'BO',
       boardpoint_offpoint_desc VARCHAR(255) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_boardpoint_offpoint 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkboardpoint_offp ON snap_boardpoint_offpoint
(
       boardpoint_offpoint_cd         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_boardpoint_offpoint
       ADD CONSTRAINT xpkboardpoint_offp PRIMARY KEY (
              boardpoint_offpoint_cd);


CREATE TABLE snap_booking_class (
       booking_class_cd     CHAR(2) NOT NULL,
       class_type_cd        CHAR(1) NOT NULL,
       off_peak_ind         CHAR(1) NOT NULL,
       booking_class_default_desc VARCHAR(255) NOT NULL,
       booking_class_desc_odi BIGINT,
       default_config_class_cd CHAR(2) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_booking_class 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkbooking_class ON snap_booking_class
(
       booking_class_cd               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_booking_class
       ADD CONSTRAINT xpkbooking_class PRIMARY KEY (booking_class_cd)
;

CREATE TABLE snap_booking_class_alt (
       booking_class_cd     CHAR(2) NOT NULL,
       class_type_cd        CHAR(1) NOT NULL,
       off_peak_ind         CHAR(1) NOT NULL,
       booking_class_default_desc VARCHAR(255) NOT NULL,
       booking_class_desc_odi BIGINT,
       default_config_class_cd CHAR(2) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_booking_class_alt 
 	PCTFREE 0
;
 	 
 	 
CREATE UNIQUE INDEX xpkbooking_class_alt ON snap_booking_class_alt
(
       booking_class_cd               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS
;


ALTER TABLE snap_booking_class_alt
       ADD CONSTRAINT xpkbooking_class PRIMARY KEY (booking_class_cd)
;

CREATE TABLE snap_cabotage_operating_country (
       cabotage_agreement_id BIGINT NOT NULL,
       operating_country_id BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_cabotage_operating_country 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKCABOTAGE_OPERAT ON snap_cabotage_operating_country
(
       cabotage_agreement_id          ASC,
       operating_country_id           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_cabotage_operating_country
       ADD CONSTRAINT XPKCABOTAGE_OPERAT PRIMARY KEY (
              cabotage_agreement_id, operating_country_id);


CREATE TABLE snap_carrier (
       carrier_id           BIGINT NOT NULL,
       status_change_reason_id BIGINT,
       party_id             BIGINT NOT NULL,
       carrier_coding_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       carrier_scheme_rank  SMALLINT NOT NULL DEFAULT 1,
       party_role_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       role_type_id         BIGINT NOT NULL,
       party_role_status_cd CHAR(1) NOT NULL,
       party_role_update_shift_cd CHAR(1) DEFAULT 'N',
       iata_membership_ind  CHAR(1) NOT NULL DEFAULT 'N',
       oper_base_feature_id BIGINT,
       travel_service_type_cd CHAR(2),
       carrier_type_id      BIGINT,
       party_role_service_start_date DATE,
       num_of_rooms_cnt     INTEGER,
       lower_single_room_rate_amt NUMERIC(24,4),
       upper_double_room_rate_amt NUMERIC(24,4),
       corporate_room_rate_amt NUMERIC(24,4),
       oag_cargo_duplicate_ind CHAR(1) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_carrier 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKcarrier ON snap_carrier
(
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_carrier
       ADD CONSTRAINT XPKcarrier PRIMARY KEY (carrier_id);


CREATE TABLE snap_carrier_booking_class (
       carrier_id           BIGINT NOT NULL,
       booking_class_cd     CHAR(2) NOT NULL,
       carrier_booking_class_cd CHAR(2) NOT NULL,
       carrier_config_class_cd CHAR(2) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_carrier_booking_class 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcarrier_booking ON snap_carrier_booking_class
(
       booking_class_cd               ASC,
       carrier_id                     ASC,
       carrier_booking_class_cd       ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_carrier_booking_class
       ADD CONSTRAINT xpkcarrier_booking PRIMARY KEY (
              booking_class_cd, carrier_id, carrier_booking_class_cd);


CREATE TABLE snap_carrier_coding (
       coding_scheme_id     BIGINT NOT NULL,
       carrier_coding_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       scheme_carrier_cd    VARCHAR(8) NOT NULL,
       carrier_scheme_rank  SMALLINT NOT NULL DEFAULT 1,
       carrier_coding_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       carrier_id           BIGINT NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_carrier_coding 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcarrier_coding ON snap_carrier_coding
(
       coding_scheme_id               ASC,
       carrier_coding_start_date      ASC,
       scheme_carrier_cd              ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_carrier_coding
       ADD CONSTRAINT xpkcarrier_coding PRIMARY KEY (coding_scheme_id, 
              carrier_coding_start_date, scheme_carrier_cd, 
              carrier_id);


CREATE TABLE snap_carrier_default_optional_element (
       optional_element_num INTEGER NOT NULL,
       carrier_optional_element_value VARCHAR(20) NOT NULL,
       carrier_id           BIGINT NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_carrier_default_optional_element 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcarrier_default ON snap_carrier_default_optional_element
(
       optional_element_num           ASC,
       carrier_optional_element_value ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_carrier_default_optional_element
       ADD CONSTRAINT xpkcarrier_default PRIMARY KEY (
              optional_element_num, carrier_optional_element_value, 
              carrier_id);


CREATE TABLE snap_carrier_sched_version (
       sched_version_name_id BIGINT NOT NULL,
       carrier_id           BIGINT NOT NULL,
       srvc_num_1_digit_lead_zero_cnt SMALLINT DEFAULT 0,
       srvc_num_2_digit_lead_zero_cnt SMALLINT DEFAULT 0,
       srvc_num_3_digit_lead_zero_cnt SMALLINT DEFAULT 0,
       manual_validation_required_ind CHAR(1) NOT NULL,
       process_ssm_in_schedules_ind CHAR(1) NOT NULL,
       process_ssm_in_ofs_ind CHAR(1) NOT NULL,
       pre_process_rule_file_name VARCHAR(100)
)
	 IN SCHD
;


ALTER TABLE snap_carrier_sched_version 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKCARRIER_SCHEDUL ON snap_carrier_sched_version
(
       sched_version_name_id          ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_carrier_sched_version
       ADD CONSTRAINT XPKCARRIER_SCHEDUL PRIMARY KEY (
              sched_version_name_id, carrier_id);


CREATE TABLE snap_carrier_schedule_version_port_default_terminal (
       carrier_id           BIGINT NOT NULL,
       sched_version_name_id BIGINT NOT NULL,
       port_id              BIGINT NOT NULL,
       default_dep_terminal_id BIGINT,
       default_arr_terminal_id BIGINT
)
	 IN REF
;


ALTER TABLE snap_carrier_schedule_version_port_default_terminal 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKsnap_carrier_sc ON snap_carrier_schedule_version_port_default_terminal
(
       carrier_id                     ASC,
       sched_version_name_id          ASC,
       port_id                        ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_carrier_schedule_version_port_default_terminal
       ADD CONSTRAINT XPKsnap_carrier_sc PRIMARY KEY (carrier_id, 
              sched_version_name_id, port_id);


CREATE TABLE snap_case (
       case_id              BIGINT NOT NULL,
       case_status_type_id  BIGINT NOT NULL,
       case_desc            VARCHAR(255) NOT NULL,
       case_start_datetime  DATE NOT NULL DEFAULT CURRENT DATE,
       case_end_datetime    DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01')
)
	 IN REF
;


ALTER TABLE snap_case 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcase ON snap_case
(
       case_id                        ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_case
       ADD CONSTRAINT xpkcase PRIMARY KEY (case_id);


CREATE TABLE snap_case_comment (
       case_id              BIGINT NOT NULL,
       case_comment_timestamp TIMESTAMP NOT NULL,
       case_comment_username VARCHAR(40),
       case_comment_txt     VARCHAR(512)
)
	 IN REF
;


ALTER TABLE snap_case_comment 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcase_comment ON snap_case_comment
(
       case_id                        ASC,
       case_comment_timestamp         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_case_comment
       ADD CONSTRAINT xpkcase_comment PRIMARY KEY (case_id, 
              case_comment_timestamp);


CREATE TABLE snap_case_status_type (
       case_status_type_id  BIGINT NOT NULL,
       case_status_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_case_status_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcase_status_typ ON snap_case_status_type
(
       case_status_type_id            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_case_status_type
       ADD CONSTRAINT xpkcase_status_typ PRIMARY KEY (
              case_status_type_id);


CREATE TABLE snap_class_type_code (
       class_type_cd        CHAR(1) NOT NULL,
       class_type_desc      VARCHAR(255) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_class_type_code 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKclass_type_cd ON snap_class_type_code
(
       class_type_cd                  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_class_type_code
       ADD CONSTRAINT XPKclass_type_cd PRIMARY KEY (class_type_cd);


CREATE TABLE snap_coding_scheme (
       coding_scheme_id     BIGINT NOT NULL,
       coding_scheme_name   VARCHAR(100) NOT NULL,
       version_date         DATE NOT NULL,
       coding_scheme_category_id BIGINT NOT NULL,
       scheme_usage_type_id BIGINT NOT NULL,
       coding_scheme_owner_id BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_coding_scheme 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcoding_scheme ON snap_coding_scheme
(
       coding_scheme_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_coding_scheme
       ADD CONSTRAINT xpkcoding_scheme PRIMARY KEY (coding_scheme_id);


CREATE TABLE snap_coding_scheme_category (
       coding_scheme_category_id BIGINT NOT NULL,
       coding_scheme_category_desc VARCHAR(255) NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_coding_scheme_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKCODING_SCHEME_C ON snap_coding_scheme_category
(
       coding_scheme_category_id      ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_coding_scheme_category
       ADD CONSTRAINT XPKCODING_SCHEME_C PRIMARY KEY (
              coding_scheme_category_id);


CREATE TABLE snap_coding_scheme_usage (
       scheme_usage_type_id BIGINT NOT NULL,
       scheme_usage_desc    VARCHAR(255) NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_coding_scheme_usage 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKCODING_SCHEME_U ON snap_coding_scheme_usage
(
       scheme_usage_type_id           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_coding_scheme_usage
       ADD CONSTRAINT XPKCODING_SCHEME_U PRIMARY KEY (
              scheme_usage_type_id);


CREATE TABLE snap_comm_event (
       comm_event_id        BIGINT NOT NULL,
       case_id              BIGINT NOT NULL,
       comm_event_purpose_id BIGINT NOT NULL,
       comm_event_start_datetime TIMESTAMP NOT NULL,
       comm_event_end_datetime TIMESTAMP,
       comm_event_username  VARCHAR(40),
       comm_event_comment_txt VARCHAR(4000),
       relationship_id      BIGINT,
       party_relationship_start_date DATE DEFAULT CURRENT DATE
)
	 IN REF_LARGE
;


ALTER TABLE snap_comm_event 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcommunication_e ON snap_comm_event
(
       comm_event_id                  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_comm_event
       ADD CONSTRAINT xpkcommunication_e PRIMARY KEY (comm_event_id);


CREATE TABLE snap_comm_event_purpose (
       comm_event_purpose_id BIGINT NOT NULL,
       comm_event_purpose_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_comm_event_purpose 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcom_event_purp ON snap_comm_event_purpose
(
       comm_event_purpose_id          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_comm_event_purpose
       ADD CONSTRAINT xpkcom_event_purp PRIMARY KEY (
              comm_event_purpose_id);


CREATE TABLE snap_comm_event_work_effort (
       comm_event_id        BIGINT NOT NULL,
       work_effort_id       BIGINT NOT NULL,
       comms_event_work_effort_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_comm_event_work_effort 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcom_event_wf ON snap_comm_event_work_effort
(
       work_effort_id                 ASC,
       comm_event_id                  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_comm_event_work_effort
       ADD CONSTRAINT xpkcom_event_wf PRIMARY KEY (work_effort_id, 
              comm_event_id);


CREATE TABLE snap_config_class (
       config_class_cd      CHAR(2) NOT NULL,
       class_type_cd        CHAR(1) NOT NULL,
       config_class_desc_odi BIGINT,
       config_class_desc    VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_config_class 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkconfig_class ON snap_config_class
(
       config_class_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_config_class
       ADD CONSTRAINT xpkconfig_class PRIMARY KEY (config_class_cd);


CREATE TABLE snap_config_combination (
       config_combination_id BIGINT NOT NULL,
       service_type_cd      CHAR(1) NOT NULL,
       service_haul_category_cd CHAR(1),
       oper_purpose_category_cd CHAR(2)
)
	 IN REF
;


ALTER TABLE snap_config_combination 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkconfig_combinat ON snap_config_combination
(
       config_combination_id          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_config_combination
       ADD CONSTRAINT xpkconfig_combinat PRIMARY KEY (
              config_combination_id);


CREATE TABLE snap_contact_method (
       contact_method_id    BIGINT NOT NULL,
       contact_method_type_id BIGINT NOT NULL,
       electronic_address   VARCHAR(255),
       intl_dial_cd_txt     VARCHAR(10),
       area_cd_txt          VARCHAR(9),
       tel_num_txt          VARCHAR(10),
       ext_num_txt          VARCHAR(5),
       address_1_line       VARCHAR(40),
       address_2_line       VARCHAR(40),
       address_3_line       VARCHAR(40),
       address_4_line       VARCHAR(40),
       address_city_line    VARCHAR(40),
       address_state_province_line VARCHAR(40),
       post_cd              VARCHAR(15),
       cntry_name           VARCHAR(100)
)
	 IN REF
;


ALTER TABLE snap_contact_method 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcontact_method ON snap_contact_method
(
       contact_method_id              ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_contact_method
       ADD CONSTRAINT xpkcontact_method PRIMARY KEY (
              contact_method_id);


CREATE TABLE snap_contact_method_purpose_type (
       contact_method_purpose_type_cd BIGINT NOT NULL,
       contact_method_purp_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_contact_method_purpose_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcntct_meth_purp ON snap_contact_method_purpose_type
(
       contact_method_purpose_type_cd ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_contact_method_purpose_type
       ADD CONSTRAINT xpkcntct_meth_purp PRIMARY KEY (
              contact_method_purpose_type_cd);


CREATE TABLE snap_contact_method_type (
       contact_method_type_id BIGINT NOT NULL,
       contact_method_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_contact_method_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcntct_meth_type ON snap_contact_method_type
(
       contact_method_type_id         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_contact_method_type
       ADD CONSTRAINT xpkcntct_meth_type PRIMARY KEY (
              contact_method_type_id);


CREATE TABLE snap_credit_card (
       credit_card_cd       VARCHAR(4) NOT NULL,
       credit_card_name     VARCHAR(100) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_credit_card 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcredit_card ON snap_credit_card
(
       credit_card_cd                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_credit_card
       ADD CONSTRAINT xpkcredit_card PRIMARY KEY (credit_card_cd);


CREATE TABLE snap_credit_card_restriction (
       credit_card_restriction_cd VARCHAR(4) NOT NULL,
       credit_card_restriction_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_credit_card_restriction 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcredit_card_res ON snap_credit_card_restriction
(
       credit_card_restriction_cd     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_credit_card_restriction
       ADD CONSTRAINT xpkcredit_card_res PRIMARY KEY (
              credit_card_restriction_cd);


CREATE TABLE snap_currency (
       currency_id          BIGINT NOT NULL,
       currency_name_odi    BIGINT,
       currency_name        VARCHAR(100) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_currency 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcurrency ON snap_currency
(
       currency_id                    ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_currency
       ADD CONSTRAINT xpkcurrency PRIMARY KEY (currency_id);


CREATE TABLE snap_currency_coding (
       currency_id          BIGINT NOT NULL,
       currency_cd          VARCHAR(3) NOT NULL,
       coding_scheme_id     BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_currency_coding 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkcurrency_coding ON snap_currency_coding
(
       currency_id                    ASC,
       currency_cd                    ASC,
       coding_scheme_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_currency_coding
       ADD CONSTRAINT xpkcurrency_coding PRIMARY KEY (currency_id, 
              currency_cd, coding_scheme_id);


CREATE TABLE snap_default_terminal_equipment_override (
       carrier_id           BIGINT NOT NULL,
       sched_version_name_id BIGINT NOT NULL,
       port_id              BIGINT NOT NULL,
       equipment_type_id    BIGINT NOT NULL,
       service_type_cd      CHAR(1) NOT NULL,
       service_range_start_num INTEGER NOT NULL,
       service_range_end_num INTEGER NOT NULL,
       override_dep_terminal_id BIGINT NOT NULL,
       override_arr_terminal_id BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_default_terminal_equipment_override 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKdefault_termina ON snap_default_terminal_equipment_override
(
       carrier_id                     ASC,
       sched_version_name_id          ASC,
       port_id                        ASC,
       equipment_type_id              ASC,
       service_type_cd                ASC,
       service_range_start_num        ASC,
       service_range_end_num          ASC,
       override_dep_terminal_id       ASC,
       override_arr_terminal_id       ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_default_terminal_equipment_override
       ADD CONSTRAINT XPKdefault_termina PRIMARY KEY (carrier_id, 
              sched_version_name_id, port_id, equipment_type_id, 
              service_type_cd, service_range_start_num, 
              service_range_end_num, override_dep_terminal_id, 
              override_arr_terminal_id);


CREATE TABLE snap_display_txt_use (
       display_txt_use_cd   CHAR(1) NOT NULL,
       display_txt_use_desc VARCHAR(255) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_display_txt_use 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKdisplay_txt_use ON snap_display_txt_use
(
       display_txt_use_cd             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_display_txt_use
       ADD CONSTRAINT XPKdisplay_txt_use PRIMARY KEY (
              display_txt_use_cd);


CREATE TABLE snap_dst_variation (
       feature_id           BIGINT NOT NULL,
       dst_start_date       DATE NOT NULL,
       dst_start_time       TIME NOT NULL,
       dst_end_date         DATE NOT NULL,
       dst_end_time         TIME NOT NULL,
       dst_variation_interval_mins SMALLINT
)
	 IN REF
;


ALTER TABLE snap_dst_variation 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkdaylight_saving ON snap_dst_variation
(
       feature_id                     ASC,
       dst_start_date                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_dst_variation
       ADD CONSTRAINT xpkdaylight_saving PRIMARY KEY (feature_id, 
              dst_start_date);


CREATE TABLE snap_equipment_cabin_config (
       equipment_config_id  BIGINT NOT NULL,
       cabin_seqnum         SMALLINT NOT NULL,
       config_class_cd      CHAR(2) NOT NULL,
       seats_qty            SMALLINT,
       cargo_capacity_weight DECIMAL(8,2),
       max_seat_recline_angle SMALLINT,
       seat_pitch_dist      DECIMAL(11,3),
       seat_width_dist      DECIMAL(11,3),
       default_seat_block_layout VARCHAR(20),
       cabin_start_seat_cd  VARCHAR(4) NOT NULL,
       cabin_end_seat_cd    VARCHAR(4) NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_timestamp TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_equipment_cabin_config 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKEQUIP_CABIN_CON ON snap_equipment_cabin_config
(
       cabin_seqnum                   ASC,
       equipment_config_id            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_cabin_config
       ADD CONSTRAINT XPKEQUIP_CABIN_CON PRIMARY KEY (cabin_seqnum, 
              equipment_config_id);


CREATE TABLE snap_equipment_cabin_seat_block_exception (
       cabin_seqnum         SMALLINT NOT NULL,
       equipment_config_id  BIGINT NOT NULL,
       seat_block_numeric   INTEGER NOT NULL,
       seat_exception_type_cd CHAR(1) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_equipment_cabin_seat_block_exception 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKEQUIP_CABIN_SBX ON snap_equipment_cabin_seat_block_exception
(
       cabin_seqnum                   ASC,
       equipment_config_id            ASC,
       seat_block_numeric             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_cabin_seat_block_exception
       ADD CONSTRAINT XPKEQUIP_CABIN_SBX PRIMARY KEY (cabin_seqnum, 
              equipment_config_id, seat_block_numeric);


CREATE TABLE snap_equipment_category (
       equipment_category_cd CHAR(1) NOT NULL,
       equipment_category_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_equipment_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkequipment_categ ON snap_equipment_category
(
       equipment_category_cd          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_category
       ADD CONSTRAINT xpkequipment_categ PRIMARY KEY (
              equipment_category_cd);


CREATE TABLE snap_equipment_coding (
       coding_scheme_id     BIGINT NOT NULL,
       equipment_type_id    BIGINT NOT NULL,
       equipment_type_cd    VARCHAR(8) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_equipment_coding 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkequipment_codin ON snap_equipment_coding
(
       coding_scheme_id               ASC,
       equipment_type_cd              ASC,
       equipment_type_id              ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_coding
       ADD CONSTRAINT xpkequipment_codin PRIMARY KEY (
              coding_scheme_id, equipment_type_cd, equipment_type_id);
              

CREATE TABLE snap_equipment_additional_coding (
       coding_scheme_id     BIGINT NOT NULL,
       equipment_type_id    BIGINT NOT NULL,
       equipment_type_cd    VARCHAR(8) NOT NULL
)
	 IN REF;


ALTER TABLE snap_equipment_additional_coding 
 	 PCTFREE 0;
 
CREATE UNIQUE INDEX xpkequipment_additional_codin ON snap_equipment_additional_coding
(
       coding_scheme_id               ASC,
       equipment_type_cd              ASC,
       equipment_type_id              ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS;


ALTER TABLE snap_equipment_additional_coding
       ADD CONSTRAINT xpkequipment_additional_codin PRIMARY KEY (
              coding_scheme_id, equipment_type_cd, equipment_type_id);


CREATE TABLE snap_equipment_config (
       equipment_config_id  BIGINT NOT NULL,
       equipment_type_id    BIGINT NOT NULL,
       config_combination_id BIGINT NOT NULL,
       party_role_id        BIGINT,
       equipment_variant_seqnum SMALLINT NOT NULL,
       fast_cabin_reconfig_ind CHAR(1),
       seats_data_source_cd CHAR(2),
       cargo_data_source_cd CHAR(2),
       cabin_config_diag_graphic_url VARCHAR(255),
       cabin_config_diag_graphic_odi BIGINT,
       cabin_config_diag_txt VARCHAR(4000),
       cabin_config_diag_txt_odi BIGINT,
       config_remarks_txt   VARCHAR(255),
       min_config_seats_qty SMALLINT,
       max_config_seats_qty SMALLINT,
       passenger_capacity_qty SMALLINT,
       fleet_size_qty       SMALLINT
)
	 IN REF_LARGE
;


ALTER TABLE snap_equipment_config 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKEQUIPMENT_CONFI ON snap_equipment_config
(
       equipment_config_id            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_config
       ADD CONSTRAINT XPKEQUIPMENT_CONFI PRIMARY KEY (
              equipment_config_id);


CREATE TABLE snap_equipment_family (
       equipment_family_cd  VARCHAR(4) NOT NULL,
       equipment_family_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_equipment_family 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkequipment_famil ON snap_equipment_family
(
       equipment_family_cd            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_family
       ADD CONSTRAINT xpkequipment_famil PRIMARY KEY (
              equipment_family_cd);


CREATE TABLE snap_equipment_group (
       equipment_group_cd   VARCHAR(4) NOT NULL,
       equipment_family_cd  VARCHAR(4) NOT NULL,
       manufacturer_id      BIGINT,
       equipment_group_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_equipment_group 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkequipment_group ON snap_equipment_group
(
       equipment_group_cd             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_group
       ADD CONSTRAINT xpkequipment_group PRIMARY KEY (
              equipment_group_cd);


CREATE TABLE snap_equipment_manufacturer_coding (
       coding_scheme_id     BIGINT NOT NULL,
       equip_manuf_coding_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       scheme_equip_manufacturer_cd VARCHAR(8) NOT NULL,
       equipment_manufacturer_id BIGINT NOT NULL,
       equip_manuf_coding_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       equip_manuf_scheme_rank SMALLINT NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_equipment_manufacturer_coding 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKequip_manf_cdg ON snap_equipment_manufacturer_coding
(
       coding_scheme_id               ASC,
       equipment_manufacturer_id      ASC,
       equip_manuf_coding_start_date  ASC,
       scheme_equip_manufacturer_cd   ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_manufacturer_coding
       ADD CONSTRAINT XPKequip_manf_cdg PRIMARY KEY (coding_scheme_id, 
              equipment_manufacturer_id, 
              equip_manuf_coding_start_date, 
              scheme_equip_manufacturer_cd);


CREATE TABLE snap_equipment_seat_block_exception_type (
       seat_exception_type_cd CHAR(1) NOT NULL,
       seat_exception_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_equipment_seat_block_exception_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkequipment_seat_ ON snap_equipment_seat_block_exception_type
(
       seat_exception_type_cd         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_seat_block_exception_type
       ADD CONSTRAINT xpkequipment_seat_ PRIMARY KEY (
              seat_exception_type_cd);


CREATE TABLE snap_equipment_type (
       equipment_type_id    BIGINT NOT NULL,
       equipment_category_cd CHAR(1),
       equipment_group_cd   VARCHAR(4) NOT NULL,
       acft_body_type_cd    CHAR(1),
       equipment_name       VARCHAR(100) NOT NULL,
       max_range_dist       DECIMAL(11,3),
       max_speed            INTEGER,
       default_cargo_class_cd CHAR(2),
       max_payload_weight   DECIMAL(8,2),
       cargo_hold_volume    DECIMAL(8,3),
       equipment_length     DECIMAL(11,3),
       cabin_pressurised_ind CHAR(1),
       num_of_engines_cnt   SMALLINT,
       unladen_weight       DECIMAL(8,2),
       max_takeoff_weight   DECIMAL(8,2),
       wingspan_length      DECIMAL(11,3),
       rail_gauge_dist      DECIMAL(11,3),
       displacement_weight  DECIMAL(8,2),
       axle_loading_kg      DECIMAL(8,2),
       num_of_wheels_cnt    SMALLINT,
       equipment_name_odi   BIGINT,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL,
       valid_for_schedules_use_ind CHAR(1)
)
	 IN REF
;


ALTER TABLE snap_equipment_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkequipment_type ON snap_equipment_type
(
       equipment_type_id              ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_type
       ADD CONSTRAINT xpkequipment_type PRIMARY KEY (
              equipment_type_id);


CREATE TABLE snap_equipment_valid_service_type (
       equipment_type_id    BIGINT NOT NULL,
       service_type_cd      CHAR(1) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_equipment_valid_service_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkequipment_valid ON snap_equipment_valid_service_type
(
       equipment_type_id              ASC,
       service_type_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_equipment_valid_service_type
       ADD CONSTRAINT xpkequipment_valid PRIMARY KEY (
              equipment_type_id, service_type_cd);


CREATE TABLE snap_feature (
       feature_id           BIGINT NOT NULL,
       feature_type_desc    VARCHAR(255) NOT NULL,
       eff_end_date         DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       feature_default_name VARCHAR(100) NOT NULL,
       feature_name_odi     BIGINT,
       intl_dial_cd         VARCHAR(10),
       sub_cntry_intl_dial_cd VARCHAR(10),
       sub_cntry_combine_dial_cd_ind CHAR(1),
       time_division_desc   VARCHAR(511),
       time_division_desc_odi BIGINT,
       std_variation_interval_mins SMALLINT,
       lat                  DECIMAL(8,5),
       long                 DECIMAL(8,5),
       altitude             DECIMAL(11,3),
       urban_area_dist      DECIMAL(11,3),
       urban_area_direction VARCHAR(3),
       otp_ind              CHAR(1),
       feature_display_seqnum INTEGER,
       feature_type_id      BIGINT NOT NULL,
       feature_owning_role_name VARCHAR(100),
       active_feature_ind   CHAR(1),
       feature_scheme_rank  SMALLINT,
       valid_for_scheds_ind CHAR(1) NOT NULL,
       coding_eff_end_date  DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01')
)
	 IN REF
;


ALTER TABLE snap_feature 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfeature ON snap_feature
(
       feature_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE1SNAP_FEATURE ON snap_feature
(
       feature_type_id                ASC,
       active_feature_ind             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE2SNAP_FEATURE ON snap_feature
(
       feature_id                     ASC,
       active_feature_ind             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature
       ADD CONSTRAINT xpkfeature PRIMARY KEY (feature_id);


CREATE TABLE snap_feature_assoc (
       feature_assoc_id     BIGINT NOT NULL,
       feature_from_id      BIGINT NOT NULL,
       feature_to_id        BIGINT NOT NULL,
       feature_assoc_type_id BIGINT NOT NULL,
       feature_assoc_type_desc VARCHAR(255) NOT NULL,
       eff_end_date         DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       port_display_seqnum  SMALLINT
)
	 IN REF
;


ALTER TABLE snap_feature_assoc 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfut_assc ON snap_feature_assoc
(
       feature_assoc_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE UNIQUE INDEX XA1K_FUT_ASSC ON snap_feature_assoc
(
       feature_from_id                ASC,
       feature_to_id                  ASC,
       feature_assoc_type_id          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIF1SNAP_FEATURE_A ON snap_feature_assoc
(
       feature_from_id                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIF2SNAP_FEATURE_A ON snap_feature_assoc
(
       feature_assoc_type_id          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIF3SNAP_FEATURE_A ON snap_feature_assoc
(
       feature_to_id                  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_assoc
       ADD CONSTRAINT xpkfut_assc PRIMARY KEY (feature_assoc_id);


CREATE TABLE snap_feature_assoc_txt (
       feature_txt_type_id  BIGINT NOT NULL,
       feature_assoc_id     BIGINT NOT NULL,
       feature_assoc_txt_odi BIGINT,
       feature_assoc_txt    VARCHAR(4000)
)
	 IN REF_LARGE
;


ALTER TABLE snap_feature_assoc_txt 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfut_assc_txt ON snap_feature_assoc_txt
(
       feature_txt_type_id            ASC,
       feature_assoc_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_assoc_txt
       ADD CONSTRAINT xpkfut_assc_txt PRIMARY KEY (
              feature_txt_type_id, feature_assoc_id);


CREATE TABLE snap_feature_assoc_type (
       feature_assoc_type_id BIGINT NOT NULL,
       feature_from_type_id BIGINT NOT NULL,
       feature_to_type_id   BIGINT NOT NULL,
       feature_assoc_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_feature_assoc_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfut_assc_typ ON snap_feature_assoc_type
(
       feature_assoc_type_id          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_assoc_type
       ADD CONSTRAINT xpkfut_assc_typ PRIMARY KEY (
              feature_assoc_type_id);


CREATE TABLE snap_feature_coding (
       coding_scheme_id     BIGINT NOT NULL,
       feature_cd           VARCHAR(10) NOT NULL,
       feature_id           BIGINT NOT NULL,
       effective_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       feature_scheme_rank  SMALLINT,
       eff_end_date         DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       valid_for_scheds_ind CHAR(1) NOT NULL,
       feature_type_coding_scheme_id BIGINT,
       feature_type_cd      CHAR(1)
)
	 IN REF
;


ALTER TABLE snap_feature_coding 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfeature_coding ON snap_feature_coding
(
       coding_scheme_id               ASC,
       feature_cd                     ASC,
       feature_id                     ASC,
       effective_start_date           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_coding
       ADD CONSTRAINT xpkfeature_coding PRIMARY KEY (coding_scheme_id, 
              feature_cd, feature_id, effective_start_date);


CREATE TABLE snap_feature_currency (
       feature_id           BIGINT NOT NULL,
       currency_id          BIGINT NOT NULL,
       effective_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       effective_end_date   DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01')
)
	 IN REF
;


ALTER TABLE snap_feature_currency 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfeature_currenc ON snap_feature_currency
(
       feature_id                     ASC,
       currency_id                    ASC,
       effective_start_date           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_currency
       ADD CONSTRAINT xpkfeature_currenc PRIMARY KEY (feature_id, 
              currency_id, effective_start_date);


CREATE TABLE snap_feature_group (
       feature_group_id     BIGINT NOT NULL,
       feature_group_desc   VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_feature_group 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKfeature_group ON snap_feature_group
(
       feature_group_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_group
       ADD CONSTRAINT XPKfeature_group PRIMARY KEY (feature_group_id);


CREATE TABLE snap_feature_lang (
       feature_id           BIGINT NOT NULL,
       lang_id              BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_feature_lang 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfeature_languag ON snap_feature_lang
(
       feature_id                     ASC,
       lang_id                        ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_lang
       ADD CONSTRAINT xpkfeature_languag PRIMARY KEY (feature_id, 
              lang_id);


CREATE TABLE snap_feature_txt (
       feature_id           BIGINT NOT NULL,
       feature_txt_type_id  BIGINT NOT NULL,
       feature_txt_odi      BIGINT,
       effective_end_date   DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       feature_txt          VARCHAR(4000),
       effective_start_date DATE NOT NULL DEFAULT CURRENT DATE
)
	 IN REF_LARGE
;


ALTER TABLE snap_feature_txt 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfeature_text ON snap_feature_txt
(
       feature_id                     ASC,
       feature_txt_type_id            ASC,
       effective_start_date           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_txt
       ADD CONSTRAINT xpkfeature_text PRIMARY KEY (feature_id, 
              feature_txt_type_id, effective_start_date);


CREATE TABLE snap_feature_txt_type (
       feature_txt_type_id  BIGINT NOT NULL,
       feature_txt_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_feature_txt_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfeature_text_ty ON snap_feature_txt_type
(
       feature_txt_type_id            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_txt_type
       ADD CONSTRAINT xpkfeature_text_ty PRIMARY KEY (
              feature_txt_type_id);


CREATE TABLE snap_feature_type (
       feature_type_id      BIGINT NOT NULL,
       feature_type_desc    VARCHAR(255) NOT NULL,
       feature_group_id     BIGINT
)
	 IN REF
;


ALTER TABLE snap_feature_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfeature_type ON snap_feature_type
(
       feature_type_id                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_type
       ADD CONSTRAINT xpkfeature_type PRIMARY KEY (feature_type_id);


CREATE TABLE snap_feature_type_coding (
       coding_scheme_id     BIGINT NOT NULL,
       feature_type_cd      CHAR(1) NOT NULL,
       feature_type_scheme_rank SMALLINT
)
	 IN REF
;


ALTER TABLE snap_feature_type_coding 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfeature_type_cd ON snap_feature_type_coding
(
       coding_scheme_id               ASC,
       feature_type_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_type_coding
       ADD CONSTRAINT xpkfeature_type_cd PRIMARY KEY (
              coding_scheme_id, feature_type_cd);


CREATE TABLE snap_feature_type_coding_assoc (
       coding_scheme_id     BIGINT NOT NULL,
       feature_type_cd      CHAR(1) NOT NULL,
       feature_type_id      BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_feature_type_coding_assoc 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkfeature_type_ca ON snap_feature_type_coding_assoc
(
       coding_scheme_id               ASC,
       feature_type_cd                ASC,
       feature_type_id                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_type_coding_assoc
       ADD CONSTRAINT xpkfeature_type_ca PRIMARY KEY (
              coding_scheme_id, feature_type_cd, feature_type_id);


CREATE TABLE snap_feature_version (
       feature_id           BIGINT NOT NULL,
       eff_start_date       DATE NOT NULL DEFAULT CURRENT DATE,
       eff_end_date         DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       feature_default_name VARCHAR(100) NOT NULL,
       feature_name_odi     BIGINT,
       intl_dial_cd         VARCHAR(10),
       sub_cntry_intl_dial_cd VARCHAR(10),
       sub_cntry_combine_dial_cd_ind CHAR(1),
       time_division_desc   VARCHAR(511),
       time_division_desc_odi BIGINT,
       std_variation_interval_mins SMALLINT,
       lat                  DECIMAL(8,5),
       long                 DECIMAL(8,5),
       altitude             DECIMAL(11,3),
       urban_area_dist      DECIMAL(11,3),
       urban_area_direction VARCHAR(3),
       otp_ind              CHAR(1),
       feature_display_seqnum INTEGER,
       fictitious_feature_ind CHAR(1) NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_feature_version 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKfeature_version ON snap_feature_version
(
       feature_id                     ASC,
       eff_start_date                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_feature_version
       ADD CONSTRAINT XPKfeature_version PRIMARY KEY (feature_id, 
              eff_start_date);


CREATE TABLE snap_input_profile_option (
       processing_profile_id BIGINT NOT NULL,
       profile_option_id    BIGINT NOT NULL,
       input_profile_option_value VARCHAR(20) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_input_profile_option 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkinput_profile_o ON snap_input_profile_option
(
       processing_profile_id          ASC,
       profile_option_id              ASC,
       input_profile_option_value     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_input_profile_option
       ADD CONSTRAINT xpkinput_profile_o PRIMARY KEY (
              processing_profile_id, profile_option_id, 
              input_profile_option_value);


CREATE TABLE snap_inter_carrier_relationship_txt (
       party_role_2_id      BIGINT NOT NULL,
       party_role_1_id      BIGINT NOT NULL,
       inter_carrier_relationship_txt VARCHAR(4000),
       inter_carrier_rel_txt_odi BIGINT
)
	 IN REF_LARGE
;


ALTER TABLE snap_inter_carrier_relationship_txt 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKintercarr_r_txt ON snap_inter_carrier_relationship_txt
(
       party_role_2_id                ASC,
       party_role_1_id                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE UNIQUE INDEX XAK1intercarr_r_tx ON snap_inter_carrier_relationship_txt
(
       party_role_1_id                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_inter_carrier_relationship_txt
       ADD CONSTRAINT XPKintercarr_r_txt PRIMARY KEY (party_role_2_id, 
              party_role_1_id);


CREATE TABLE snap_interline_agreement_type (
       interline_agreement_type_num INTEGER NOT NULL,
       interline_agreement_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_interline_agreement_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKinterline_aggrm ON snap_interline_agreement_type
(
       interline_agreement_type_num   ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_interline_agreement_type
       ADD CONSTRAINT XPKinterline_aggrm PRIMARY KEY (
              interline_agreement_type_num);


CREATE TABLE snap_intl_domestic_status (
       intl_domestic_status_cd CHAR(1) NOT NULL,
       intl_domestic_status_desc VARCHAR(60) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_intl_domestic_status 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkinternational_d ON snap_intl_domestic_status
(
       intl_domestic_status_cd        ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_intl_domestic_status
       ADD CONSTRAINT xpkinternational_d PRIMARY KEY (
              intl_domestic_status_cd);


CREATE TABLE snap_iv (
       iv_id                BIGINT NOT NULL,
       sched_version_name   VARCHAR(100) NOT NULL,
       release_sell_end_date DATE DEFAULT SYSIBM.DATE('2038-01-01'),
       sched_version_id     BIGINT NOT NULL,
       service_num          INTEGER NOT NULL,
       carrier_scheme_rank  SMALLINT NOT NULL DEFAULT 1,
       service_desc         VARCHAR(255),
       carrier_coding_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       iv_num               SMALLINT NOT NULL,
       service_desc_odi     BIGINT,
       sched_status_cd      CHAR(1) NOT NULL,
       iv_eff_start_date    DATE NOT NULL DEFAULT CURRENT DATE,
       iv_eff_end_date      DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       oper_days_of_week    VARCHAR(20),
       freq_rate_num        SMALLINT,
       modifier_ind         CHAR(1) NOT NULL DEFAULT 'S',
       oper_suffix          CHAR(1),
       service_dep_seqnum   SMALLINT DEFAULT 1,
       service_dep_desc     VARCHAR(255),
       ssim_update_allowed_ind CHAR(1),
       carrier_id           BIGINT NOT NULL,
       oper_prefix          CHAR(1),
       last_update_username VARCHAR(40) NOT NULL,
       last_update_timestamp TIMESTAMP NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_iv 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkitinerary_varia ON snap_iv
(
       iv_id                          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIF1snap_iv ON snap_iv
(
       service_num                    ASC,
       sched_version_id               ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE1SNAP_IV ON snap_iv
(
       iv_eff_end_date                ASC,
       oper_days_of_week              ASC,
       iv_id                          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_iv
       ADD CONSTRAINT xpkitinerary_varia PRIMARY KEY (iv_id);


CREATE TABLE snap_lang (
       lang_id              BIGINT NOT NULL,
       lang_name            VARCHAR(100) NOT NULL,
       lang_name_odi        BIGINT
)
	 IN REF
;


ALTER TABLE snap_lang 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpklanguage ON snap_lang
(
       lang_id                        ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_lang
       ADD CONSTRAINT xpklanguage PRIMARY KEY (lang_id);


CREATE TABLE snap_language_coding (
       coding_scheme_id     BIGINT NOT NULL,
       lang_id              BIGINT NOT NULL,
       lang_cd              VARCHAR(8) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_language_coding 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKlanguage_coding ON snap_language_coding
(
       coding_scheme_id               ASC,
       lang_id                        ASC,
       lang_cd                        ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_language_coding
       ADD CONSTRAINT XPKlanguage_coding PRIMARY KEY (
              coding_scheme_id, lang_id, lang_cd);


CREATE TABLE snap_leg_booking_class (
       leg_seg_id           BIGINT NOT NULL,
       booking_class_cd     CHAR(2) NOT NULL,
       seats_qty            SMALLINT,
       booking_modifier_cd  CHAR(1),
       meal_cd_list         VARCHAR(255)
)
	 IN LEGSEG_XML
;


ALTER TABLE snap_leg_booking_class 
 	 PCTFREE 0 
 ;
CREATE INDEX XIE1LEG_BOOKING_CL ON snap_leg_booking_class
(
       leg_seg_id                     ASC,
       booking_class_cd               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE2LEG_BOOKING_CL ON snap_leg_booking_class
(
       leg_seg_id                     ASC,
       booking_modifier_cd            ASC,
       booking_class_cd               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


CREATE TABLE snap_leg_config_class (
       leg_seg_id           BIGINT NOT NULL,
       config_class_cd      CHAR(2) NOT NULL,
       units_qty            SMALLINT
)
	 IN LEGSEG_XML
;


ALTER TABLE snap_leg_config_class 
 	 PCTFREE 0 
 ;
CREATE INDEX XIE1LEG_CONFIG_CLA ON snap_leg_config_class
(
       leg_seg_id                     ASC,
       config_class_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


CREATE TABLE snap_leg_dupl_xref (
       leg_seg_id           BIGINT NOT NULL,
       participant_type_cd  VARCHAR(4) NOT NULL,
       carrier_id           BIGINT NOT NULL,
       service_num          INTEGER,
       oper_suffix          CHAR(1),
       original_seqnum      SMALLINT,
       component_leg_seqnum SMALLINT
)
	 IN LEGSEG_XML
;


ALTER TABLE snap_leg_dupl_xref 
 	 PCTFREE 0 
 ;
CREATE INDEX XIE1LEG_DUPL_XREF ON snap_leg_dupl_xref
(
       leg_seg_id                     ASC,
       participant_type_cd            ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


CREATE TABLE snap_leg_opt_element (
       leg_seg_id           BIGINT NOT NULL,
       optional_element_num INTEGER NOT NULL,
       optional_element_value VARCHAR(255)
)
	 IN LEGSEG_XML
;


ALTER TABLE snap_leg_opt_element 
 	 PCTFREE 0 
 ;
CREATE INDEX XIE1LEG_OPT_ELEMEN ON snap_leg_opt_element
(
       leg_seg_id                     ASC,
       optional_element_num           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


CREATE TABLE snap_leg_participant (
       leg_seg_id           BIGINT NOT NULL,
       participant_type_cd  VARCHAR(4) NOT NULL,
       participant_party_role_id BIGINT,
       participant_party_role_name_id BIGINT,
       component_leg_seqnum SMALLINT
)
	 IN LEGSEG_XML
;


ALTER TABLE snap_leg_participant 
 	 PCTFREE 0 
 ;
CREATE INDEX XIE1LEG_PARTICIPAN ON snap_leg_participant
(
       leg_seg_id                     ASC,
       participant_type_cd            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


CREATE TABLE snap_leg_partnership (
       leg_seg_id           BIGINT NOT NULL,
       partnership_party_role_id BIGINT NOT NULL
)
	 IN LEGSEG_XML
;


ALTER TABLE snap_leg_partnership 
 	 PCTFREE 0 
 ;
CREATE INDEX XIE1LEG_PARTNERSHI ON snap_leg_partnership
(
       leg_seg_id                     ASC,
       partnership_party_role_id      ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


CREATE TABLE snap_leg_seg (
       leg_seg_id           BIGINT NOT NULL,
       sched_version_name   VARCHAR(100) NOT NULL,
       release_sell_end_date DATE DEFAULT SYSIBM.DATE('2038-01-01'),
       service_type_cd      CHAR(1),
       sched_version_id     BIGINT NOT NULL,
       service_num          INTEGER NOT NULL,
       iv_id                BIGINT,
       carrier_id           BIGINT,
       arr_terminal_id      BIGINT,
       dep_port_id          BIGINT,
       dep_terminal_id      BIGINT,
       arr_port_id          BIGINT,
       sched_status_cd      CHAR(1) NOT NULL,
       iv_num               SMALLINT NOT NULL,
       service_desc         VARCHAR(255),
       carrier_scheme_rank  SMALLINT NOT NULL DEFAULT 1,
       service_desc_odi     BIGINT,
       dep_dst_var_interval_mins SMALLINT,
       arr_dst_var_interval_mins SMALLINT,
       iv_eff_start_date    DATE NOT NULL DEFAULT CURRENT DATE,
       iv_eff_end_date      DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       oper_days_of_week    VARCHAR(20),
       dep_std_var_interval_mins SMALLINT,
       arr_std_var_interval_mins SMALLINT,
       freq_rate_num        SMALLINT DEFAULT 1,
       carrier_coding_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       oper_suffix          CHAR(1),
       service_dep_seqnum   SMALLINT DEFAULT 1,
       service_dep_desc     VARCHAR(255),
       equipment_type_id    BIGINT,
       num_of_stops_cnt     INTEGER,
       dupl_non_oper_leg_cd CHAR(1),
       equipment_registration VARCHAR(40),
       equipment_version_cd VARCHAR(40),
       sched_equipment_dep_time TIME,
       sched_equipment_arr_time TIME,
       sched_pax_dep_time   TIME,
       sched_pax_arr_time   TIME,
       dep_interval_days    SMALLINT,
       arr_interval_days    SMALLINT,
       transit_layover_interval_days SMALLINT,
       bilateral_information_cd CHAR(2),
       onward_service_carrier_id BIGINT,
       onward_service_num   INTEGER,
       onward_service_oper_suffix CHAR(1),
       onwd_srvc_layover_intvl_days SMALLINT,
       modifier_cancels_traffic_ind CHAR(1),
       seg_enhanced_ind     CHAR(1) NOT NULL,
       inbound_intl_dom_status_cd CHAR(1),
       outbound_intl_dom_status_cd CHAR(1),
       modifiers_applied_ind CHAR(1) NOT NULL,
       schd_equipment_dep_utc TIME,
       sched_equipment_arr_utc TIME,
       sched_pax_dep_utc    TIME,
       sched_pax_arr_utc    TIME,
       elapsed_time_interval_mins SMALLINT,
       equip_group_cd       CHAR(3),
       arr_european_status_ind CHAR(1) NOT NULL,
       dep_european_status_ind CHAR(1) NOT NULL,
       arr_schengen_status_ind CHAR(1) NOT NULL,
       dep_schengen_status_ind CHAR(1) NOT NULL,
       leg_seqnum           SMALLINT,
       sched_equip_dep_2utc_diff_mins SMALLINT,
       sched_equip_arr_2utc_diff_mins SMALLINT,
       sched_pax_dep_2utc_diff_mins SMALLINT,
       sched_pax_arr_2utc_diff_mins SMALLINT,
       config_class_xml     DB2XML.XMLVARCHAR,
       duplicate_leg_xml    DB2XML.XMLVARCHAR,
       traffic_restriction_xml DB2XML.XMLVARCHAR,
       booking_class_xml    DB2XML.XMLVARCHAR,
       optional_element_xml DB2XML.XMLVARCHAR,
       partnership_xml      DB2XML.XMLVARCHAR,
       participant_xml      DB2XML.XMLVARCHAR,
       great_circle_dist    DECIMAL(11,3),
       release_sell_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       override_dep_terminal_id BIGINT,
       override_arr_terminal_id BIGINT,
       tctf_cd              VARCHAR(4) NOT NULL DEFAULT 'TF',
       leg_seg_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       leg_seg_eff_end_date DATE NOT NULL DEFAULT CURRENT DATE,
       dep_city_id          BIGINT NOT NULL WITH DEFAULT,
       arr_city_id          BIGINT NOT NULL WITH DEFAULT,
       num_of_stops_excl_fict_cnt INTEGER,
       optional_element_num_list VARCHAR(511),
       routing_feature_id_list VARCHAR(1000),
       seg_arr_interval_days SMALLINT,
       check_in_carrier_id  BIGINT,
       check_in_txt         VARCHAR(255),
       travel_time_interval_mins SMALLINT,
       transfer_time_interval_mins SMALLINT,
       equip_owner_carrier_id BIGINT,
       equip_pilot_carrier_id BIGINT,
       equip_crew_carrier_id BIGINT,
       oper_prefix          CHAR(1),
       cabin_first_meal_cd_list VARCHAR(30),
       cabin_business_meal_cd_list VARCHAR(30),
       cabin_economy_meal_cd_list VARCHAR(30),
       oag_calc_intl_dom_stat_cd_ind CHAR(1) NOT NULL DEFAULT 'N'
)
	 IN LEGSEG_DATA
;


ALTER TABLE snap_leg_seg 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkleg_segment ON snap_leg_seg
(
       leg_seg_id                     ASC,
       seg_enhanced_ind               ASC,
       modifiers_applied_ind          ASC
)
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIF1snap_leg_seg ON snap_leg_seg
(
       iv_id                          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE1snap_leg_seg ON snap_leg_seg
(
       carrier_id                     ASC,
       service_num                    ASC,
       iv_id                          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE8snap_leg_seg ON snap_leg_seg
(
       leg_seg_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE3snap_leg_seg ON snap_leg_seg
(
       dep_city_id                    ASC,
       arr_city_id                    ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE4snap_leg_seg ON snap_leg_seg
(
       leg_seg_id                     ASC,
       sched_version_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE6snap_leg_seg ON snap_leg_seg
(
       dep_port_id                    ASC,
       arr_port_id                    ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE7snap_leg_seg ON snap_leg_seg
(
       sched_version_name             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_leg_seg
       ADD CONSTRAINT xpkleg_segment PRIMARY KEY (leg_seg_id, 
              seg_enhanced_ind, modifiers_applied_ind);


CREATE TABLE snap_leg_traffic_rest (
       leg_seg_id           BIGINT NOT NULL,
       traffic_restriction_cd CHAR(1) NOT NULL,
       bo_cd_list           VARCHAR(10),
       pcm_cd_list          VARCHAR(10)
)
	 IN LEGSEG_XML
;


ALTER TABLE snap_leg_traffic_rest 
 	 PCTFREE 0 
 ;
CREATE INDEX XIE1LEG_TRAFFIC_RE ON snap_leg_traffic_rest
(
       leg_seg_id                     ASC,
       traffic_restriction_cd         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


CREATE TABLE snap_market_sector_category (
       market_seg_category_id BIGINT NOT NULL,
       market_seg_category_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_market_sector_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkmarket_sector_c ON snap_market_sector_category
(
       market_seg_category_id         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_market_sector_category
       ADD CONSTRAINT xpkmarket_sector_c PRIMARY KEY (
              market_seg_category_id);


CREATE TABLE snap_market_sector_feature_category (
       market_sector_feature_cat_id BIGINT NOT NULL,
       market_sector_feature_cat_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_market_sector_feature_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKMARKET_SECTOR_F ON snap_market_sector_feature_category
(
       market_sector_feature_cat_id   ASC
)
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_market_sector_feature_category
       ADD CONSTRAINT XPKMARKET_SECTOR_F PRIMARY KEY (
              market_sector_feature_cat_id);


CREATE TABLE snap_market_sector_property (
       market_seg_prop_id   BIGINT NOT NULL,
       market_seg_category_id BIGINT NOT NULL,
       market_seg_prop_desc VARCHAR(255) NOT NULL,
       property_data_type_cd CHAR(1) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_market_sector_property 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKmkt_sector_prop ON snap_market_sector_property
(
       market_seg_prop_id             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_market_sector_property
       ADD CONSTRAINT XPKmkt_sector_prop PRIMARY KEY (
              market_seg_prop_id);


CREATE TABLE snap_market_sector_property_valid_value (
       market_seg_prop_id   BIGINT NOT NULL,
       party_mkt_seg_prop_valid_value VARCHAR(20) NOT NULL,
       mkt_seg_prop_valid_value_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_market_sector_property_valid_value 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKmkt_sec_prop_vv ON snap_market_sector_property_valid_value
(
       market_seg_prop_id             ASC,
       party_mkt_seg_prop_valid_value ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_market_sector_property_valid_value
       ADD CONSTRAINT XPKmkt_sec_prop_vv PRIMARY KEY (
              market_seg_prop_id, party_mkt_seg_prop_valid_value);


CREATE TABLE snap_mct_connection_combination (
       inbound_intl_dom_status_cd CHAR(1) NOT NULL,
       outbound_intl_dom_status_cd CHAR(1) NOT NULL,
       mct_connection_desc  VARCHAR(60) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_mct_connection_combination 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKMCT_CONNECTION_ ON snap_mct_connection_combination
(
       inbound_intl_dom_status_cd     ASC,
       outbound_intl_dom_status_cd    ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE UNIQUE INDEX XAK1MCT_CONNECTION ON snap_mct_connection_combination
(
       mct_connection_desc            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_mct_connection_combination
       ADD CONSTRAINT XPKMCT_CONNECTION_ PRIMARY KEY (
              inbound_intl_dom_status_cd, outbound_intl_dom_status_cd);


CREATE TABLE snap_mct_rule (
       mct_rule_id          INTEGER NOT NULL,
       last_update_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT TIMESTAMP,
       mct_level_seqnum     SMALLINT,
       inbound_intl_dom_status_cd CHAR(1),
       outbound_intl_dom_status_cd CHAR(1),
       mct_rule_type_id     BIGINT,
       eff_start_date       DATE NOT NULL DEFAULT CURRENT DATE,
       eff_end_date         DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       connect_time         SMALLINT,
       connect_time_txt     VARCHAR(255),
       product_id           BIGINT,
       inbound_service_type_cd CHAR(1),
       outbound_service_type_cd CHAR(1),
       inbound_service_category_cd CHAR(1),
       outbound_service_category_cd CHAR(1),
       active_rule_ind      CHAR(1) NOT NULL DEFAULT 'Y',
       reverse_application_ind CHAR(1),
       apply_feature_exception_ind CHAR(1),
       connection_disallowed_ind CHAR(1),
       excep_inbound_service_type_cd CHAR(1),
       excep_outbound_service_type_cd CHAR(1),
       inbound_flt_range_start INTEGER,
       inbound_flt_range_end INTEGER,
       outbound_flt_range_start INTEGER,
       outbound_carrier_id  BIGINT,
       orig_feature_id      BIGINT,
       dest_feature_id      BIGINT,
       arr_transfer_point_id BIGINT,
       dep_transfer_point_id BIGINT,
       inbound_carrier_id   BIGINT,
       outbound_flt_range_end INTEGER,
       inbound_acft_body_type_cd CHAR(1),
       outbound_acft_body_type_cd CHAR(1),
       inbound_equip_group_cd VARCHAR(4),
       outbound_equip_group_cd VARCHAR(4),
       inbound_config_class_cd CHAR(2),
       outbound_config_class_cd CHAR(2),
       parent_mct_rule_id   BIGINT,
       parent_mct_rule_update_date TIMESTAMP,
       override_inbound_status_cd CHAR(1),
       override_outbound_status_cd CHAR(1),
       override_ib_feature_id BIGINT,
       override_ob_feature_id BIGINT,
       override_carrier_id  BIGINT,
       override_flight_range_start INTEGER,
       override_priority_rank SMALLINT,
       override_flight_range_end INTEGER,
       arr_xfer_pt_feature_type_id BIGINT,
       dep_xfer_pt_feature_type_id BIGINT,
       orig_feature_type_id BIGINT,
       dest_feature_type_id BIGINT,
       mct_rule_type_pcm_cat_cd CHAR(1) NOT NULL,
       parent_dep_transfer_point_id BIGINT,
       parent_arr_transfer_point_id BIGINT,
       wright_amendment_ind CHAR(1) NOT NULL DEFAULT 'N',
       override_dom_leg_intl_seg_ind CHAR(1),
       last_update_time     TIMESTAMP NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       ib_flt_range_start_oper_prefix CHAR(1),
       ib_flt_range_start_oper_suffix CHAR(1),
       ob_flt_range_start_oper_prefix CHAR(1),
       ob_flt_range_start_oper_suffix CHAR(1),
       ib_flt_range_end_oper_prefix CHAR(1),
       ib_flt_range_end_oper_suffix CHAR(1),
       ob_flt_range_end_oper_prefix CHAR(1),
       ob_flt_range_end_oper_suffix CHAR(1),
       or_flt_range_start_oper_prefix CHAR(1),
       or_flt_range_start_oper_suffix CHAR(1),
       or_flt_range_end_oper_prefix CHAR(1),
       or_flt_range_end_oper_suffix CHAR(1)
)
	 IN REF
;


ALTER TABLE snap_mct_rule 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkmct_rule ON snap_mct_rule
(
       mct_rule_id                    ASC,
       last_update_timestamp          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_mct_rule
       ADD CONSTRAINT xpkmct_rule PRIMARY KEY (mct_rule_id, 
              last_update_timestamp);


CREATE TABLE snap_mct_rule_type (
       mct_rule_type_id     BIGINT NOT NULL,
       pcm_category_cd      CHAR(1) NOT NULL,
       mct_rule_type_desc   VARCHAR(255) NOT NULL,
       mct_weighting_factor SMALLINT,
       mct_display_seqnum   SMALLINT DEFAULT 1
)
	 IN REF
;


ALTER TABLE snap_mct_rule_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkmct_rule_type ON snap_mct_rule_type
(
       mct_rule_type_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_mct_rule_type
       ADD CONSTRAINT xpkmct_rule_type PRIMARY KEY (mct_rule_type_id);


CREATE TABLE snap_meal_service (
       meal_service_cd      CHAR(1) NOT NULL,
       meal_service_default_desc VARCHAR(255) NOT NULL,
       meal_service_desc_odi BIGINT
)
	 IN SCHD
;


ALTER TABLE snap_meal_service 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkmeal_service ON snap_meal_service
(
       meal_service_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_meal_service
       ADD CONSTRAINT xpkmeal_service PRIMARY KEY (meal_service_cd);


CREATE TABLE snap_modifier_action (
       action_cd            VARCHAR(4) NOT NULL,
       action_desc          VARCHAR(255) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_modifier_action 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkmodifier_action ON snap_modifier_action
(
       action_cd                      ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_modifier_action
       ADD CONSTRAINT xpkmodifier_action PRIMARY KEY (action_cd);


CREATE TABLE snap_modifier_change_reason (
       change_reason_cd     VARCHAR(4) NOT NULL,
       change_reason_desc   VARCHAR(255) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_modifier_change_reason 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkmodifier_change ON snap_modifier_change_reason
(
       change_reason_cd               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_modifier_change_reason
       ADD CONSTRAINT xpkmodifier_change PRIMARY KEY (
              change_reason_cd);


CREATE TABLE snap_odi_option (
       odi                  BIGINT NOT NULL,
       odi_option_num       SMALLINT NOT NULL,
       lang_id              BIGINT NOT NULL,
       odi_option_txt       VARCHAR(16000),
       odi_option_category_id BIGINT,
       option_cat_lang_seqnum SMALLINT
)
	 IN LANG_LARGE
;


ALTER TABLE snap_odi_option 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkodi_option ON snap_odi_option
(
       odi                            ASC,
       odi_option_num                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_odi_option
       ADD CONSTRAINT xpkodi_option PRIMARY KEY (odi, odi_option_num);


CREATE TABLE snap_odi_option_category (
       odi_option_category_id BIGINT NOT NULL,
       odi_option_category_desc VARCHAR(255) NOT NULL
)
	 IN LANG
;


ALTER TABLE snap_odi_option_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkodi_option_cate ON snap_odi_option_category
(
       odi_option_category_id         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_odi_option_category
       ADD CONSTRAINT xpkodi_option_cate PRIMARY KEY (
              odi_option_category_id);


CREATE TABLE snap_oper_purpose_category (
       oper_purpose_category_cd CHAR(2) NOT NULL,
       oper_purpose_category_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_oper_purpose_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkoperation_purpo ON snap_oper_purpose_category
(
       oper_purpose_category_cd       ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_oper_purpose_category
       ADD CONSTRAINT xpkoperation_purpo PRIMARY KEY (
              oper_purpose_category_cd);


CREATE TABLE snap_optional_element (
       optional_element_num INTEGER NOT NULL,
       optional_element_type_cd CHAR(1) NOT NULL,
       comment_group_num    INTEGER,
       comment_std_name     VARCHAR(100) NOT NULL,
       comment_name_odi     BIGINT,
       comment_desc         VARCHAR(2000) NOT NULL,
       comment_xml_name     VARCHAR(100) NOT NULL,
       direct_flt_use_ind   CHAR(1) NOT NULL,
       transfer_connection_use_ind CHAR(1) NOT NULL,
       intl_use_ind         CHAR(1) NOT NULL,
       show_comment_details_ind CHAR(1) NOT NULL,
       element_use_cd       CHAR(1) NOT NULL,
       element_data_type_cd CHAR(1) NOT NULL,
       element_leg_use_cd   CHAR(1) NOT NULL DEFAULT '0'
)
	 IN REF
;


ALTER TABLE snap_optional_element 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkservice_option ON snap_optional_element
(
       optional_element_num           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_optional_element
       ADD CONSTRAINT xpkservice_option PRIMARY KEY (
              optional_element_num);


CREATE TABLE snap_optional_element_display_use (
       optional_element_num INTEGER NOT NULL,
       display_txt_use_cd   CHAR(1) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_optional_element_display_use 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKopt_elmnt_disp ON snap_optional_element_display_use
(
       optional_element_num           ASC,
       display_txt_use_cd             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_optional_element_display_use
       ADD CONSTRAINT XPKopt_elmnt_disp PRIMARY KEY (
              optional_element_num, display_txt_use_cd);


CREATE TABLE snap_optional_element_endpoint_use (
       optional_element_num INTEGER NOT NULL,
       boardpoint_offpoint_cd VARCHAR(2) NOT NULL DEFAULT 'BO'
)
	 IN REF
;


ALTER TABLE snap_optional_element_endpoint_use 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKopt_elmnt_use ON snap_optional_element_endpoint_use
(
       optional_element_num           ASC,
       boardpoint_offpoint_cd         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_optional_element_endpoint_use
       ADD CONSTRAINT XPKopt_elmnt_use PRIMARY KEY (
              optional_element_num, boardpoint_offpoint_cd);


CREATE TABLE snap_optional_element_pcm_category (
       pcm_category_cd      CHAR(1) NOT NULL,
       optional_element_num INTEGER NOT NULL
)
	 IN REF
;


ALTER TABLE snap_optional_element_pcm_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKopt_elmnt_pcm ON snap_optional_element_pcm_category
(
       pcm_category_cd                ASC,
       optional_element_num           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_optional_element_pcm_category
       ADD CONSTRAINT XPKopt_elmnt_pcm PRIMARY KEY (pcm_category_cd, 
              optional_element_num);


CREATE TABLE snap_optional_element_type (
       optional_element_type_cd CHAR(1) NOT NULL,
       optional_element_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_optional_element_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKopt_elmnt_typ ON snap_optional_element_type
(
       optional_element_type_cd       ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_optional_element_type
       ADD CONSTRAINT XPKopt_elmnt_typ PRIMARY KEY (
              optional_element_type_cd);


CREATE TABLE snap_optional_element_valid_value (
       optional_element_num INTEGER NOT NULL,
       optional_element_valid_value VARCHAR(20) NOT NULL,
       optnl_elemt_valid_value_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_optional_element_valid_value 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKopt_elmnt_vv ON snap_optional_element_valid_value
(
       optional_element_num           ASC,
       optional_element_valid_value   ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_optional_element_valid_value
       ADD CONSTRAINT XPKopt_elmnt_vv PRIMARY KEY (
              optional_element_num, optional_element_valid_value);


CREATE TABLE snap_otp_history (
       otp_date             DATE NOT NULL DEFAULT CURRENT DATE,
       carrier_id           BIGINT NOT NULL,
       service_number       INTEGER NOT NULL,
       departure_port_id    BIGINT NOT NULL,
       arrival_port_id      BIGINT NOT NULL,
       departure_time       TIME NOT NULL,
       otp_pcnt             DECIMAL(18,3),
       pcnt_qualifier_cd    CHAR(1) NOT NULL,
       sched_version_name_id BIGINT NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_otp_history 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkon_time_perform ON snap_otp_history
(
       otp_date                       ASC,
       carrier_id                     ASC,
       sched_version_name_id          ASC,
       service_number                 ASC,
       departure_port_id              ASC,
       arrival_port_id                ASC,
       departure_time                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_otp_history
       ADD CONSTRAINT xpkon_time_perform PRIMARY KEY (otp_date, 
              carrier_id, sched_version_name_id, service_number, 
              departure_port_id, arrival_port_id, departure_time);

CREATE TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT
(
	CARRIER_ID BIGINT NOT NULL,
	SCHED_VERSION_NAME_ID BIGINT NOT NULL,
	OTP_TEXT_DATE DATE NOT NULL WITH DEFAULT CURRENT DATE,
	SERVICE_NUMBER INTEGER NOT NULL,
	DEPARTURE_PORT_ID BIGINT NOT NULL,
	ARRIVAL_PORT_ID BIGINT NOT NULL,
	ARRIVAL_TIME TIME NOT NULL,
	OTP_TEXT VARCHAR(50),
	LAST_UPDATE_USERNAME VARCHAR(40) NOT NULL,
	LAST_UPDATE_TIME TIMESTAMP WITH DEFAULT CURRENT TIMESTAMP,
	CONSTRAINT XPKOTP_HIST_TEXT PRIMARY KEY(OTP_TEXT_DATE, CARRIER_ID, SCHED_VERSION_NAME_ID, SERVICE_NUMBER, DEPARTURE_PORT_ID, ARRIVAL_PORT_ID, ARRIVAL_TIME)
)
IN SCHD;

CREATE TABLE snap_output_profile_option (
       processing_profile_id BIGINT NOT NULL,
       profile_option_id    BIGINT NOT NULL,
       output_profile_option_value VARCHAR(20) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_output_profile_option 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkoutput_profile_ ON snap_output_profile_option
(
       processing_profile_id          ASC,
       profile_option_id              ASC,
       output_profile_option_value    ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_output_profile_option
       ADD CONSTRAINT xpkoutput_profile_ PRIMARY KEY (
              processing_profile_id, profile_option_id, 
              output_profile_option_value);


CREATE TABLE snap_participant_type (
       participant_type_cd  VARCHAR(4) NOT NULL,
       participant_type_desc VARCHAR(255) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_participant_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparticipant_typ ON snap_participant_type
(
       participant_type_cd            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_participant_type
       ADD CONSTRAINT xpkparticipant_typ PRIMARY KEY (
              participant_type_cd);


CREATE TABLE snap_party (
       party_id             BIGINT NOT NULL,
       party_type_id        BIGINT NOT NULL,
       contact_person_prefix VARCHAR(8),
       contact_person_initials CHAR(3),
       contact_person_given_name VARCHAR(100),
       contact_person_surname VARCHAR(100),
       contact_person_job_title VARCHAR(60),
       service_prov_name    VARCHAR(100),
       organization_num     INTEGER
)
	 IN REF
;


ALTER TABLE snap_party 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparty ON snap_party
(
       party_id                       ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party
       ADD CONSTRAINT xpkparty PRIMARY KEY (party_id);


CREATE TABLE snap_party_comment (
       party_id             BIGINT NOT NULL,
       party_comment_timestamp TIMESTAMP NOT NULL,
       part_comment_username VARCHAR(40) NOT NULL,
       party_comment_txt    VARCHAR(4000)
)
	 IN REF_LARGE
;


ALTER TABLE snap_party_comment 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparty_comment ON snap_party_comment
(
       party_id                       ASC,
       party_comment_timestamp        ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_comment
       ADD CONSTRAINT xpkparty_comment PRIMARY KEY (party_id, 
              party_comment_timestamp);


CREATE TABLE snap_party_contact_method (
       party_id             BIGINT NOT NULL,
       contact_method_id    BIGINT NOT NULL,
       contact_method_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       role_type_id         BIGINT,
       eff_end_date         DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       comment_txt          VARCHAR(255),
       comment_txt_odi      BIGINT,
       party_contact_method_rank SMALLINT,
       ext_num_txt          VARCHAR(5),
       suppress_intl_dial_cd_ind CHAR(1) NOT NULL DEFAULT 'Y'
)
	 IN REF
;


ALTER TABLE snap_party_contact_method 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparty_cntct_mth ON snap_party_contact_method
(
       party_id                       ASC,
       contact_method_id              ASC,
       contact_method_eff_start_date  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_contact_method
       ADD CONSTRAINT xpkparty_cntct_mth PRIMARY KEY (party_id, 
              contact_method_id, contact_method_eff_start_date);


CREATE TABLE snap_party_contact_method_purpose (
       party_id             BIGINT NOT NULL,
       contact_method_id    BIGINT NOT NULL,
       contact_method_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       contact_method_purpose_type_cd BIGINT NOT NULL,
       purpose_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       purpose_eff_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       party_cntct_meth_purp_txt VARCHAR(255),
       party_cntct_meth_purp_txt_odi BIGINT
)
	 IN REF
;


ALTER TABLE snap_party_contact_method_purpose 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparty_cntct_m_p ON snap_party_contact_method_purpose
(
       party_id                       ASC,
       contact_method_id              ASC,
       contact_method_eff_start_date  ASC,
       contact_method_purpose_type_cd ASC,
       purpose_eff_start_date         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_contact_method_purpose
       ADD CONSTRAINT xpkparty_cntct_m_p PRIMARY KEY (party_id, 
              contact_method_id, contact_method_eff_start_date, 
              contact_method_purpose_type_cd, purpose_eff_start_date);


CREATE TABLE snap_party_feature_contact_method_purpose (
       carrier_feature_contact_id BIGINT NOT NULL,
       party_id             BIGINT NOT NULL,
       feature_id           BIGINT NOT NULL,
       contact_method_id    BIGINT NOT NULL,
       contact_method_purpose_type_cd BIGINT NOT NULL,
       contact_method_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       purpose_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       contact_method_purpose_rank SMALLINT
)
	 IN REF
;


ALTER TABLE snap_party_feature_contact_method_purpose 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkancillary_area_ ON snap_party_feature_contact_method_purpose
(
       carrier_feature_contact_id     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_feature_contact_method_purpose
       ADD CONSTRAINT xpkancillary_area_ PRIMARY KEY (
              carrier_feature_contact_id);


CREATE TABLE snap_party_market_sector (
       party_role_id        BIGINT NOT NULL,
       market_seg_category_id BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_party_market_sector 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparty_market_se ON snap_party_market_sector
(
       party_role_id                  ASC,
       market_seg_category_id         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_market_sector
       ADD CONSTRAINT xpkparty_market_se PRIMARY KEY (party_role_id, 
              market_seg_category_id);


CREATE TABLE snap_party_market_sector_equipment (
       party_market_seg_equip_id BIGINT NOT NULL,
       market_seg_category_id BIGINT NOT NULL,
       equip_group_cd       VARCHAR(4),
       equip_type_id        BIGINT,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL,
       party_role_id        BIGINT NOT NULL,
       equipment_type_id    BIGINT
)
	 IN REF
;


ALTER TABLE snap_party_market_sector_equipment 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKPRTY_MKT_SEC_EQ ON snap_party_market_sector_equipment
(
       party_market_seg_equip_id      ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_market_sector_equipment
       ADD CONSTRAINT XPKPRTY_MKT_SEC_EQ PRIMARY KEY (
              party_market_seg_equip_id);


CREATE TABLE snap_party_market_sector_feature (
       party_role_id        BIGINT NOT NULL,
       market_seg_category_id BIGINT NOT NULL,
       feature_id           BIGINT NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL,
       feature_use_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       feature_use_eff_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       market_sector_feature_cat_id BIGINT,
       official_start_date_year_num INTEGER,
       official_start_date_month_num INTEGER,
       official_start_date_day_num INTEGER,
       official_end_date_year_num INTEGER,
       official_end_date_month_num INTEGER,
       official_end_date_day_num INTEGER
)
	 IN REF
;


ALTER TABLE snap_party_market_sector_feature 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKPRY_MRKT_SEC_FE ON snap_party_market_sector_feature
(
       market_seg_category_id         ASC,
       feature_id                     ASC,
       party_role_id                  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_market_sector_feature
       ADD CONSTRAINT XPKPRY_MRKT_SEC_FE PRIMARY KEY (
              market_seg_category_id, feature_id, party_role_id);


CREATE TABLE snap_party_market_sector_property (
       party_role_id        BIGINT NOT NULL,
       market_seg_category_id BIGINT NOT NULL,
       market_seg_prop_id   BIGINT NOT NULL,
       party_mkt_seg_prop_valid_value VARCHAR(20) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_party_market_sector_property 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKparty_mkt_sec_p ON snap_party_market_sector_property
(
       party_role_id                  ASC,
       market_seg_category_id         ASC,
       market_seg_prop_id             ASC,
       party_mkt_seg_prop_valid_value ASC,
       last_update_time               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_market_sector_property
       ADD CONSTRAINT XPKparty_mkt_sec_p PRIMARY KEY (party_role_id, 
              market_seg_category_id, market_seg_prop_id, 
              party_mkt_seg_prop_valid_value, last_update_time);


CREATE TABLE snap_party_relationship (
       relationship_id      BIGINT NOT NULL,
       party_relationship_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       relationship_desc    VARCHAR(255) NOT NULL,
       relationship_type_id BIGINT NOT NULL,
       party_relationship_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       party_relationship_comment_txt VARCHAR(4000),
       parent_party_role_id BIGINT,
       official_start_date_year_num INTEGER,
       official_start_date_month_num INTEGER,
       official_start_date_day_num INTEGER,
       official_end_date_year_num INTEGER,
       official_end_date_month_num INTEGER,
       official_end_date_day_num INTEGER,
       party_relationship_disp_rank SMALLINT,
       interline_agreement_region_id BIGINT,
       interline_agreement_type_num INTEGER
)
	 IN REF_LARGE
;


ALTER TABLE snap_party_relationship 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparty_relations ON snap_party_relationship
(
       relationship_id                ASC,
       party_relationship_start_date  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_relationship
       ADD CONSTRAINT xpkparty_relations PRIMARY KEY (relationship_id, 
              party_relationship_start_date);


CREATE TABLE snap_party_relationship_feature (
       relationship_id      BIGINT NOT NULL,
       party_relationship_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       feature_id           BIGINT NOT NULL,
       feature_use_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       feature_use_eff_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       official_start_date_year_num INTEGER,
       official_start_date_month_num INTEGER,
       official_start_date_day_num INTEGER,
       official_end_date_year_num INTEGER,
       official_end_date_month_num INTEGER,
       official_end_date_day_num INTEGER
)
	 IN REF
;


ALTER TABLE snap_party_relationship_feature 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKPARTY_REL_FEATU ON snap_party_relationship_feature
(
       relationship_id                ASC,
       party_relationship_start_date  ASC,
       feature_id                     ASC
)
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_relationship_feature
       ADD CONSTRAINT XPKPARTY_REL_FEATU PRIMARY KEY (relationship_id, 
              party_relationship_start_date, feature_id);


CREATE TABLE snap_party_relationship_member (
       relationship_id      BIGINT NOT NULL,
       member_party_role_id BIGINT NOT NULL,
       party_relationship_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       equity_percentage    DECIMAL(18,3),
       inter_party_role_distance DECIMAL(11,3),
       relationship_member_disp_rank SMALLINT,
       member_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       member_eff_end_date  DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       official_start_date_year_num INTEGER,
       official_start_date_month_num INTEGER,
       official_start_date_day_num INTEGER,
       official_end_date_year_num INTEGER,
       official_end_date_month_num INTEGER,
       official_end_date_day_num INTEGER
)
	 IN REF
;


ALTER TABLE snap_party_relationship_member 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKPARTY_REL_MEMBR ON snap_party_relationship_member
(
       relationship_id                ASC,
       member_party_role_id           ASC,
       party_relationship_start_date  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_relationship_member
       ADD CONSTRAINT XPKPARTY_REL_MEMBR PRIMARY KEY (relationship_id, 
              member_party_role_id, party_relationship_start_date);


CREATE TABLE snap_party_role (
       party_role_id        BIGINT NOT NULL,
       party_id             BIGINT NOT NULL,
       role_type_id         BIGINT NOT NULL,
       party_role_status_cd CHAR(1) NOT NULL,
       status_change_reason_id BIGINT,
       oper_base_feature_id BIGINT,
       party_role_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       party_role_end_date  DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       party_role_service_start_date DATE,
       party_role_update_shift_cd CHAR(1) DEFAULT 'N',
       iata_membership_ind  CHAR(1),
       num_of_rooms_cnt     INTEGER,
       lower_single_room_rate_amt NUMERIC(24,4),
       upper_double_room_rate_amt NUMERIC(24,4),
       corporate_room_rate_amt NUMERIC(24,4),
       deflt_displ_coding_scheme_id BIGINT,
       deflt_displ_carr_scheme_rank SMALLINT,
       before_security_check_ind CHAR(1) DEFAULT 'Y',
       location_category_cd CHAR(1),
       added_date           DATE,
       number_of_suites_cnt INTEGER,
       free_pickup_ind      CHAR(1) DEFAULT 'N',
       free_pickup_oper_start_time TIME,
       free_pickup_oper_end_time TIME,
       room_rate_currency_id BIGINT,
       rates_publishable_ind CHAR(1) DEFAULT 'Y',
       oag_cargo_duplicate_ind CHAR(1) DEFAULT 'N'
)
	 IN REF
;


ALTER TABLE snap_party_role 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparty_role ON snap_party_role
(
       party_role_id                  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role
       ADD CONSTRAINT xpkparty_role PRIMARY KEY (party_role_id);


CREATE TABLE snap_party_role_feature (
       party_role_id        BIGINT NOT NULL,
       feature_id           BIGINT NOT NULL,
       feature_distance     DECIMAL(11,3),
       feature_distance_text VARCHAR(255),
       feature_distance_txt_odi BIGINT
)
	 IN REF
;


ALTER TABLE snap_party_role_feature 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKPARTY_ROLE_FEAT ON snap_party_role_feature
(
       party_role_id                  ASC,
       feature_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_feature
       ADD CONSTRAINT XPKPARTY_ROLE_FEAT PRIMARY KEY (party_role_id, 
              feature_id);


CREATE TABLE snap_party_role_name (
       party_role_id        BIGINT NOT NULL,
       name_type_id         BIGINT NOT NULL,
       eff_start_date       DATE NOT NULL DEFAULT CURRENT DATE,
       eff_end_date         DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       party_role_name_odi  BIGINT,
       party_role_short_name VARCHAR(100),
       party_role_short_name_odi BIGINT,
       party_role_name      VARCHAR(100) NOT NULL,
       party_role_name_id   BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_party_role_name 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparty_role_alia ON snap_party_role_name
(
       party_role_id                  ASC,
       name_type_id                   ASC,
       party_role_name                ASC,
       eff_start_date                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE UNIQUE INDEX XAK1party_role_nam ON snap_party_role_name
(
       party_role_name_id             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_name
       ADD CONSTRAINT xpkparty_role_alia PRIMARY KEY (party_role_id, 
              name_type_id, party_role_name, eff_start_date);


CREATE TABLE snap_party_role_name_type (
       name_type_id         BIGINT NOT NULL,
       name_type_desc       VARCHAR(255) NOT NULL,
       name_max_string_length_cnt INTEGER
)
	 IN REF
;


ALTER TABLE snap_party_role_name_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKp_role_name_typ ON snap_party_role_name_type
(
       name_type_id                   ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_name_type
       ADD CONSTRAINT XPKp_role_name_typ PRIMARY KEY (name_type_id);


CREATE TABLE snap_party_role_note (
       party_role_id        BIGINT NOT NULL,
       party_role_note_type_id BIGINT NOT NULL,
       party_role_note_rank SMALLINT NOT NULL,
       document_url         VARCHAR(255),
       sched_version_name_id BIGINT
)
	 IN REF
;


ALTER TABLE snap_party_role_note 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKparty_role_note ON snap_party_role_note
(
       party_role_id                  ASC,
       party_role_note_type_id        ASC,
       party_role_note_rank           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_note
       ADD CONSTRAINT XPKparty_role_note PRIMARY KEY (party_role_id, 
              party_role_note_type_id, party_role_note_rank);


CREATE TABLE snap_party_role_note_text (
       party_role_id        BIGINT NOT NULL,
       party_role_note_rank SMALLINT NOT NULL,
       party_role_note_type_id BIGINT NOT NULL,
       party_role_note_text_seqnum SMALLINT NOT NULL,
       party_role_text_category_id BIGINT,
       party_role_note_text VARCHAR(4000),
       party_role_note_text_odi BIGINT,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF_LARGE
;


ALTER TABLE snap_party_role_note_text 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKP_R_NOTE_TEXT ON snap_party_role_note_text
(
       party_role_note_rank           ASC,
       party_role_note_type_id        ASC,
       party_role_id                  ASC,
       party_role_note_text_seqnum    ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_note_text
       ADD CONSTRAINT XPKP_R_NOTE_TEXT PRIMARY KEY (
              party_role_note_rank, party_role_note_type_id, 
              party_role_id, party_role_note_text_seqnum);


CREATE TABLE snap_party_role_note_type (
       party_role_note_type_id BIGINT NOT NULL,
       party_role_note_type_desc VARCHAR(255) NOT NULL,
       role_family_id       BIGINT,
       role_type_id         BIGINT,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_party_role_note_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKpr_note_type ON snap_party_role_note_type
(
       party_role_note_type_id        ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_note_type
       ADD CONSTRAINT XPKpr_note_type PRIMARY KEY (
              party_role_note_type_id);


CREATE TABLE snap_party_role_preferred_contact_method_type (
       party_role_id        BIGINT NOT NULL,
       contact_method_type_id BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_party_role_preferred_contact_method_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKPARTY_ROLE_PREF ON snap_party_role_preferred_contact_method_type
(
       party_role_id                  ASC,
       contact_method_type_id         ASC
)
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_preferred_contact_method_type
       ADD CONSTRAINT XPKPARTY_ROLE_PREF PRIMARY KEY (party_role_id, 
              contact_method_type_id);


CREATE TABLE snap_party_role_status (
       party_role_status_cd CHAR(1) NOT NULL,
       party_role_status_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_party_role_status 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKparty_role_stat ON snap_party_role_status
(
       party_role_status_cd           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_status
       ADD CONSTRAINT XPKparty_role_stat PRIMARY KEY (
              party_role_status_cd);


CREATE TABLE snap_party_role_text_category (
       party_role_text_category_id BIGINT NOT NULL,
       party_role_text_category_desc VARCHAR(255) NOT NULL,
       party_role_text_category_odi BIGINT,
       party_role_note_type_id BIGINT NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_party_role_text_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKP_R_TEXT_CAT ON snap_party_role_text_category
(
       party_role_text_category_id    ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_text_category
       ADD CONSTRAINT XPKP_R_TEXT_CAT PRIMARY KEY (
              party_role_text_category_id);


CREATE TABLE snap_party_role_text_category_default_rank (
       party_role_text_category_id BIGINT NOT NULL,
       product_category_cd  CHAR(1) NOT NULL,
       party_role_txt_cat_deflt_rank SMALLINT,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL DEFAULT CURRENT TIMESTAMP
)
	 IN REF
;


ALTER TABLE snap_party_role_text_category_default_rank 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKP_RLE_TXT_C_D_R ON snap_party_role_text_category_default_rank
(
       party_role_text_category_id    ASC,
       product_category_cd            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_role_text_category_default_rank
       ADD CONSTRAINT XPKP_RLE_TXT_C_D_R PRIMARY KEY (
              party_role_text_category_id, product_category_cd);


CREATE TABLE snap_party_type (
       party_type_id        BIGINT NOT NULL,
       party_type_desc      VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_party_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkparty_type ON snap_party_type
(
       party_type_id                  ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_party_type
       ADD CONSTRAINT xpkparty_type PRIMARY KEY (party_type_id);


CREATE TABLE snap_pcm_category (
       pcm_category_cd      CHAR(1) NOT NULL,
       pcm_category_desc    VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_pcm_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkpcm_category ON snap_pcm_category
(
       pcm_category_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_pcm_category
       ADD CONSTRAINT xpkpcm_category PRIMARY KEY (pcm_category_cd);


CREATE TABLE snap_port (
       port_feature_id      BIGINT NOT NULL,
       european_status_ind  CHAR(1) NOT NULL,
       schengen_status_ind  CHAR(1) NOT NULL,
       port_name            VARCHAR(100) NOT NULL,
       city_id              BIGINT NOT NULL,
       country_id           BIGINT NOT NULL,
       state_id             BIGINT,
       sub_country_id       BIGINT,
       time_division_id     BIGINT
)
	 IN SCHD
;


ALTER TABLE snap_port 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKport ON snap_port
(
       port_feature_id                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_port
       ADD CONSTRAINT XPKport PRIMARY KEY (port_feature_id);


CREATE TABLE snap_processing_profile (
       processing_profile_id BIGINT NOT NULL,
       processing_profile_type_cd CHAR(1) NOT NULL,
       processing_profile_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       processing_profile_desc VARCHAR(255) NOT NULL,
       processing_profile_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       sched_version_id     BIGINT NOT NULL,
       carrier_id           BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_processing_profile 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkproc_prof ON snap_processing_profile
(
       processing_profile_id          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_processing_profile
       ADD CONSTRAINT xpkproc_prof PRIMARY KEY (processing_profile_id);


CREATE TABLE snap_processing_profile_comment (
       processing_profile_id BIGINT NOT NULL,
       profile_comment_timestamp TIMESTAMP NOT NULL,
       profile_comment_username VARCHAR(40) NOT NULL,
       profile_comment_txt  VARCHAR(4000)
)
	 IN REF_LARGE
;


ALTER TABLE snap_processing_profile_comment 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkproc_prof_com ON snap_processing_profile_comment
(
       processing_profile_id          ASC,
       profile_comment_timestamp      ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_processing_profile_comment
       ADD CONSTRAINT xpkproc_prof_com PRIMARY KEY (
              processing_profile_id, profile_comment_timestamp);


CREATE TABLE snap_processing_profile_type (
       processing_profile_type_cd CHAR(1) NOT NULL,
       processing_profile_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_processing_profile_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKproc_prof_type ON snap_processing_profile_type
(
       processing_profile_type_cd     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_processing_profile_type
       ADD CONSTRAINT XPKproc_prof_type PRIMARY KEY (
              processing_profile_type_cd);


CREATE TABLE snap_product (
       product_id           BIGINT NOT NULL,
       product_cd           VARCHAR(4) NOT NULL,
       product_desc         VARCHAR(255) NOT NULL,
       product_category_cd  CHAR(1) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_product 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkproduct ON snap_product
(
       product_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_product
       ADD CONSTRAINT xpkproduct PRIMARY KEY (product_id);


CREATE TABLE snap_product_category_code (
       product_category_cd  CHAR(1) NOT NULL,
       product_category_desc VARCHAR(255) NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_product_category_code 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKPRODUCT_CATEGOR ON snap_product_category_code
(
       product_category_cd            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_product_category_code
       ADD CONSTRAINT XPKPRODUCT_CATEGOR PRIMARY KEY (
              product_category_cd);


CREATE TABLE snap_product_override (
       product_id           BIGINT NOT NULL,
       odi                  BIGINT NOT NULL,
       odi_option_num       SMALLINT NOT NULL,
       product_override_category_cd VARCHAR(4) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_product_override 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkproduct_overrid ON snap_product_override
(
       product_id                     ASC,
       odi                            ASC,
       odi_option_num                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_product_override
       ADD CONSTRAINT xpkproduct_overrid PRIMARY KEY (product_id, odi, 
              odi_option_num);


CREATE TABLE snap_product_override_category (
       product_override_category_cd VARCHAR(4) NOT NULL,
       product_override_category_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_product_override_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkprod_orride_cat ON snap_product_override_category
(
       product_override_category_cd   ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_product_override_category
       ADD CONSTRAINT xpkprod_orride_cat PRIMARY KEY (
              product_override_category_cd);


CREATE TABLE snap_product_party_feature_contact_method (
       product_id           BIGINT NOT NULL,
       carrier_feature_contact_id BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_product_party_feature_contact_method 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKproduct_p_f_c_m ON snap_product_party_feature_contact_method
(
       product_id                     ASC,
       carrier_feature_contact_id     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_product_party_feature_contact_method
       ADD CONSTRAINT XPKproduct_p_f_c_m PRIMARY KEY (product_id, 
              carrier_feature_contact_id);


CREATE TABLE snap_product_party_role_text_category (
       product_id           BIGINT NOT NULL,
       party_role_text_category_id BIGINT NOT NULL,
       party_role_txt_cat_oride_rank SMALLINT NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_product_party_role_text_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKPROD_P_R_T_C ON snap_product_party_role_text_category
(
       party_role_text_category_id    ASC,
       product_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_product_party_role_text_category
       ADD CONSTRAINT XPKPROD_P_R_T_C PRIMARY KEY (
              party_role_text_category_id, product_id);


CREATE TABLE snap_profile_option (
       profile_option_id    BIGINT NOT NULL,
       profile_option_desc  VARCHAR(255) NOT NULL,
       profile_option_use_cd CHAR(1) NOT NULL,
       profile_option_datatype_cd CHAR(1) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_profile_option 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkprofile_option ON snap_profile_option
(
       profile_option_id              ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_profile_option
       ADD CONSTRAINT xpkprofile_option PRIMARY KEY (
              profile_option_id);


CREATE TABLE snap_profile_option_valid_value (
       profile_option_id    BIGINT NOT NULL,
       profile_option_valid_value VARCHAR(20) NOT NULL,
       prof_option_valid_value_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_profile_option_valid_value 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkprofile_opt_vv ON snap_profile_option_valid_value
(
       profile_option_id              ASC,
       profile_option_valid_value     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_profile_option_valid_value
       ADD CONSTRAINT xpkprofile_opt_vv PRIMARY KEY (
              profile_option_id, profile_option_valid_value);


CREATE TABLE snap_public_holiday (
       feature_id           BIGINT NOT NULL,
       public_holiday_date  DATE NOT NULL,
       public_holiday_name  VARCHAR(100) NOT NULL,
       depends_on_lunar_calendar_ind CHAR(1) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_public_holiday 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkpublic_holiday ON snap_public_holiday
(
       feature_id                     ASC,
       public_holiday_date            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_public_holiday
       ADD CONSTRAINT xpkpublic_holiday PRIMARY KEY (feature_id, 
              public_holiday_date);


CREATE TABLE snap_relationship_member_feature_use (
       relationship_id      BIGINT NOT NULL,
       party_relationship_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       feature_id           BIGINT NOT NULL,
       member_party_role_id BIGINT NOT NULL,
       feature_use_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       feature_use_eff_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       member_eff_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       member_eff_end_date  DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       official_start_date_year_num INTEGER,
       official_start_date_month_num INTEGER,
       official_start_date_day_num INTEGER,
       official_end_date_year_num INTEGER,
       official_end_date_month_num INTEGER,
       official_end_date_day_num INTEGER
)
	 IN REF
;


ALTER TABLE snap_relationship_member_feature_use 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKRELATIONSHIP_ME ON snap_relationship_member_feature_use
(
       relationship_id                ASC,
       party_relationship_start_date  ASC,
       feature_id                     ASC,
       member_party_role_id           ASC
)
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_relationship_member_feature_use
       ADD CONSTRAINT XPKRELATIONSHIP_ME PRIMARY KEY (relationship_id, 
              party_relationship_start_date, feature_id, 
              member_party_role_id);


CREATE TABLE snap_relationship_type (
       relationship_type_id BIGINT NOT NULL,
       parent_role_type_id  BIGINT,
       relationship_type_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_relationship_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkrelationship_ty ON snap_relationship_type
(
       relationship_type_id           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_relationship_type
       ADD CONSTRAINT xpkrelationship_ty PRIMARY KEY (
              relationship_type_id);


CREATE TABLE snap_relationship_type_member (
       relationship_type_id BIGINT NOT NULL,
       member_role_type_id  BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_relationship_type_member 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkrel_type_member ON snap_relationship_type_member
(
       relationship_type_id           ASC,
       member_role_type_id            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_relationship_type_member
       ADD CONSTRAINT xpkrel_type_member PRIMARY KEY (
              relationship_type_id, member_role_type_id);


CREATE TABLE snap_restriction_display_use (
       traffic_restriction_cd CHAR(1) NOT NULL,
       display_txt_use_cd   CHAR(1) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_restriction_display_use 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKrestr_displ_use ON snap_restriction_display_use
(
       traffic_restriction_cd         ASC,
       display_txt_use_cd             ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_restriction_display_use
       ADD CONSTRAINT XPKrestr_displ_use PRIMARY KEY (
              traffic_restriction_cd, display_txt_use_cd);


CREATE TABLE snap_restriction_validity (
       traffic_restriction_cd CHAR(1) NOT NULL,
       pcm_category_cd      VARCHAR(3) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_restriction_validity 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKrestr_validity ON snap_restriction_validity
(
       traffic_restriction_cd         ASC,
       pcm_category_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_restriction_validity
       ADD CONSTRAINT XPKrestr_validity PRIMARY KEY (
              traffic_restriction_cd, pcm_category_cd);


CREATE TABLE snap_role_family (
       role_family_id       BIGINT NOT NULL,
       role_family_desc     VARCHAR(255) NOT NULL,
       last_update_username VARCHAR(40) NOT NULL,
       last_update_time     TIMESTAMP NOT NULL
)
	 IN REF
;


ALTER TABLE snap_role_family 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKROLE_FAMILY ON snap_role_family
(
       role_family_id                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_role_family
       ADD CONSTRAINT XPKROLE_FAMILY PRIMARY KEY (role_family_id);


CREATE TABLE snap_role_type (
       role_type_id         BIGINT NOT NULL,
       role_type_desc       VARCHAR(255) NOT NULL,
       role_family_id       BIGINT NOT NULL,
       travel_service_category_txt VARCHAR(255),
       trvl_srvc_data_collection_txt VARCHAR(255),
       trvl_srvc_phone_publish_ind CHAR(1),
       default_display_seqnum SMALLINT,
       role_type_desc_odi   BIGINT
)
	 IN REF
;


ALTER TABLE snap_role_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkrole_type ON snap_role_type
(
       role_type_id                   ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_role_type
       ADD CONSTRAINT xpkrole_type PRIMARY KEY (role_type_id);


CREATE TABLE snap_sched_change_stats_detail (
       sched_version_name_id BIGINT NOT NULL,
       carrier_id           BIGINT NOT NULL,
       change_date          DATE NOT NULL,
       change_time          TIME NOT NULL,
       service_num          INTEGER NOT NULL,
       sched_change_type_num INTEGER NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_sched_change_stats_detail 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKsched_change_sd ON snap_sched_change_stats_detail
(
       sched_version_name_id          ASC,
       carrier_id                     ASC,
       change_date                    ASC,
       change_time                    ASC,
       service_num                    ASC,
       sched_change_type_num          ASC
);


ALTER TABLE snap_sched_change_stats_detail
       ADD CONSTRAINT XPKsched_change_sd PRIMARY KEY (
              sched_version_name_id, carrier_id, change_date, 
              change_time, service_num, sched_change_type_num);


CREATE TABLE snap_sched_change_stats_summary (
       sched_version_name_id BIGINT NOT NULL,
       carrier_id           BIGINT NOT NULL,
       change_date          DATE NOT NULL,
       sched_change_type_num INTEGER NOT NULL,
       change_cnt           INTEGER NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_sched_change_stats_summary 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKsched_change_ss ON snap_sched_change_stats_summary
(
       sched_version_name_id          ASC,
       carrier_id                     ASC,
       change_date                    ASC,
       sched_change_type_num          ASC
);


ALTER TABLE snap_sched_change_stats_summary
       ADD CONSTRAINT XPKsched_change_ss PRIMARY KEY (
              sched_version_name_id, carrier_id, change_date, 
              sched_change_type_num);


CREATE TABLE snap_sched_change_type (
       sched_change_type_num INTEGER NOT NULL,
       sched_change_type_desc VARCHAR(255) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_sched_change_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKsnap_sched_chan ON snap_sched_change_type
(
       sched_change_type_num          ASC
);


ALTER TABLE snap_sched_change_type
       ADD CONSTRAINT XPKsnap_sched_chan PRIMARY KEY (
              sched_change_type_num);


CREATE TABLE snap_sched_default_optional_element (
       sched_version_id     BIGINT NOT NULL,
       optional_element_num INTEGER NOT NULL,
       sched_deflt_optnl_elemt_value VARCHAR(20) NOT NULL,
       carrier_id           BIGINT NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_sched_default_optional_element 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKsched_deflt_o_e ON snap_sched_default_optional_element
(
       optional_element_num           ASC,
       sched_deflt_optnl_elemt_value  ASC,
       sched_version_id               ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_sched_default_optional_element
       ADD CONSTRAINT XPKsched_deflt_o_e PRIMARY KEY (
              optional_element_num, sched_deflt_optnl_elemt_value, 
              sched_version_id, carrier_id);


CREATE TABLE snap_sched_market_seg (
       market_seg_category_id BIGINT NOT NULL,
       sched_version_id     BIGINT NOT NULL,
       carrier_id           BIGINT NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_sched_market_seg 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkschedule_market ON snap_sched_market_seg
(
       market_seg_category_id         ASC,
       sched_version_id               ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_sched_market_seg
       ADD CONSTRAINT xpkschedule_market PRIMARY KEY (
              market_seg_category_id, sched_version_id, carrier_id);


CREATE TABLE snap_sched_version (
       sched_version_id     BIGINT NOT NULL,
       release_sell_end_date DATE DEFAULT SYSIBM.DATE('2038-01-01'),
       carrier_scheme_rank  SMALLINT NOT NULL DEFAULT 1,
       sched_version_name_id BIGINT NOT NULL,
       carrier_coding_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       sched_status_cd      CHAR(1) NOT NULL,
       carrier_id           BIGINT NOT NULL,
       release_sell_start_date DATE NOT NULL DEFAULT CURRENT DATE,
       commit_timestamp     TIMESTAMP NOT NULL DEFAULT CURRENT TIMESTAMP
)
	 IN SCHD
;


ALTER TABLE snap_sched_version 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkschedule ON snap_sched_version
(
       sched_version_id               ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE1SNAP_SCHED_VER ON snap_sched_version
(
       release_sell_end_date          ASC,
       sched_version_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_sched_version
       ADD CONSTRAINT xpkschedule PRIMARY KEY (sched_version_id, 
              carrier_id);


CREATE TABLE snap_sched_version_booking_class (
       configuration_class_code CHAR(2) NOT NULL,
       booking_class_cd     CHAR(2) NOT NULL,
       sched_version_id     BIGINT NOT NULL,
       sched_version_booking_class_cd CHAR(2) NOT NULL,
       carrier_id           BIGINT NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_sched_version_booking_class 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKsched_vsn_bkg_c ON snap_sched_version_booking_class
(
       booking_class_cd               ASC,
       sched_version_id               ASC,
       sched_version_booking_class_cd ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_sched_version_booking_class
       ADD CONSTRAINT XPKsched_vsn_bkg_c PRIMARY KEY (
              booking_class_cd, sched_version_id, 
              sched_version_booking_class_cd, carrier_id);


CREATE TABLE snap_sched_version_equipment_summary (
       sched_version_id     BIGINT NOT NULL,
       carrier_id           BIGINT NOT NULL,
       equip_id             BIGINT NOT NULL,
       codeshare_ind        CHAR(1) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_sched_version_equipment_summary 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKsched_vsn_eq_su ON snap_sched_version_equipment_summary
(
       sched_version_id               ASC,
       carrier_id                     ASC,
       equip_id                       ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_sched_version_equipment_summary
       ADD CONSTRAINT XPKsched_vsn_eq_su PRIMARY KEY (
              sched_version_id, carrier_id, equip_id);


CREATE TABLE snap_sched_version_name (
       sched_version_name_id BIGINT NOT NULL,
       sched_version_name   VARCHAR(100) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_sched_version_name 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKSCHED_VS_NAME ON snap_sched_version_name
(
       sched_version_name_id          ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_sched_version_name
       ADD CONSTRAINT XPKSCHED_VS_NAME PRIMARY KEY (
              sched_version_name_id);


CREATE TABLE snap_sched_version_port_summary (
       sched_version_id     BIGINT NOT NULL,
       carrier_id           BIGINT NOT NULL,
       port_feature_id      BIGINT NOT NULL,
       codeshare_ind        CHAR(1) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_sched_version_port_summary 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKsched_vsn_p_sum ON snap_sched_version_port_summary
(
       sched_version_id               ASC,
       carrier_id                     ASC,
       port_feature_id                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_sched_version_port_summary
       ADD CONSTRAINT XPKsched_vsn_p_sum PRIMARY KEY (
              sched_version_id, carrier_id, port_feature_id);


CREATE TABLE snap_seg_component (
       seg_id               BIGINT NOT NULL,
       seg_enhanced_ind     CHAR(1) NOT NULL,
       seg_modifiers_applied_ind CHAR(1) NOT NULL,
       component_leg_seqnum SMALLINT NOT NULL,
       component_leg_id     BIGINT NOT NULL,
       component_leg_enhanced_ind CHAR(1) NOT NULL,
       component_modifiers_appl_ind CHAR(1) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_seg_component 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKsnap_seg_compon ON snap_seg_component
(
       seg_id                         ASC,
       seg_enhanced_ind               ASC,
       component_leg_enhanced_ind     ASC,
       seg_modifiers_applied_ind      ASC,
       component_modifiers_appl_ind   ASC,
       component_leg_id               ASC,
       component_leg_seqnum           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE1snap_seg_compo ON snap_seg_component
(
       component_leg_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_seg_component
       ADD CONSTRAINT XPKsnap_seg_compon PRIMARY KEY (seg_id, 
              seg_enhanced_ind, component_leg_enhanced_ind, 
              seg_modifiers_applied_ind, component_modifiers_appl_ind, 
              component_leg_id, component_leg_seqnum);


CREATE TABLE snap_service (
       sched_version_id     BIGINT NOT NULL,
       service_num          INTEGER NOT NULL,
       release_sell_end_date DATE DEFAULT SYSIBM.DATE('2038-01-01'),
       sched_version_name   VARCHAR(100) NOT NULL,
       carrier_scheme_rank  SMALLINT NOT NULL DEFAULT 1,
       sched_status_cd      CHAR(1) NOT NULL,
       service_desc         VARCHAR(255),
       carrier_coding_end_date DATE NOT NULL DEFAULT SYSIBM.DATE('2038-01-01'),
       service_desc_odi     BIGINT,
       carrier_id           BIGINT NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_service 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkservice ON snap_service
(
       service_num                    ASC,
       sched_version_id               ASC,
       carrier_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;

CREATE INDEX XIE1SNAP_SERVICE ON snap_service
(
       service_num                    ASC,
       sched_version_id               ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_service
       ADD CONSTRAINT xpkservice PRIMARY KEY (service_num, 
              sched_version_id, carrier_id);


CREATE TABLE snap_service_category (
       service_category_cd  CHAR(1) NOT NULL,
       service_category_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_service_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkservice_categor ON snap_service_category
(
       service_category_cd            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_service_category
       ADD CONSTRAINT xpkservice_categor PRIMARY KEY (
              service_category_cd);


CREATE TABLE snap_service_haul_category (
       service_haul_category_cd CHAR(1) NOT NULL,
       service_haul_category_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_service_haul_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkservice_haul_ca ON snap_service_haul_category
(
       service_haul_category_cd       ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_service_haul_category
       ADD CONSTRAINT xpkservice_haul_ca PRIMARY KEY (
              service_haul_category_cd);


CREATE TABLE snap_service_prov_credit_card (
       credit_card_cd       VARCHAR(4) NOT NULL,
       party_id             BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_service_prov_credit_card 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpksrv_prov_cc ON snap_service_prov_credit_card
(
       credit_card_cd                 ASC,
       party_id                       ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_service_prov_credit_card
       ADD CONSTRAINT xpksrv_prov_cc PRIMARY KEY (credit_card_cd, 
              party_id);


CREATE TABLE snap_service_prov_credit_card_restriction (
       credit_card_cd       VARCHAR(4) NOT NULL,
       credit_card_restriction_cd VARCHAR(4) NOT NULL,
       party_id             BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_service_prov_credit_card_restriction 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpksrv_prov_cr ON snap_service_prov_credit_card_restriction
(
       credit_card_cd                 ASC,
       credit_card_restriction_cd     ASC,
       party_id                       ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_service_prov_credit_card_restriction
       ADD CONSTRAINT xpksrv_prov_cr PRIMARY KEY (credit_card_cd, 
              credit_card_restriction_cd, party_id);


CREATE TABLE snap_service_type (
       service_type_cd      CHAR(1) NOT NULL,
       service_category_cd  CHAR(1) NOT NULL,
       service_type_desc    VARCHAR(255) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_service_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkservice_type ON snap_service_type
(
       service_type_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_service_type
       ADD CONSTRAINT xpkservice_type PRIMARY KEY (service_type_cd);


CREATE TABLE snap_service_type_pcm_category (
       service_type_cd      CHAR(1) NOT NULL,
       pcm_category_cd      CHAR(1) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_service_type_pcm_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKservice_typ_pcm ON snap_service_type_pcm_category
(
       service_type_cd                ASC,
       pcm_category_cd                ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_service_type_pcm_category
       ADD CONSTRAINT XPKservice_typ_pcm PRIMARY KEY (service_type_cd, 
              pcm_category_cd);


CREATE TABLE snap_status_change_reason (
       status_change_reason_id BIGINT NOT NULL,
       party_role_status_cd CHAR(1) NOT NULL,
       status_change_reason_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_status_change_reason 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKstatus_chg_rsn ON snap_status_change_reason
(
       status_change_reason_id        ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_status_change_reason
       ADD CONSTRAINT XPKstatus_chg_rsn PRIMARY KEY (
              status_change_reason_id);


CREATE TABLE snap_std_form (
       std_form_id          BIGINT NOT NULL,
       std_form_name        VARCHAR(100) NOT NULL,
       std_form_electronic_location VARCHAR(255)
)
	 IN REF
;


ALTER TABLE snap_std_form 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkstandard_form ON snap_std_form
(
       std_form_id                    ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_std_form
       ADD CONSTRAINT xpkstandard_form PRIMARY KEY (std_form_id);


CREATE TABLE snap_terminal (
       terminal_feature_id  BIGINT NOT NULL,
       port_feature_id      BIGINT NOT NULL,
       feature_default_name VARCHAR(100) NOT NULL,
       feature_name_odi     BIGINT,
       feature_display_seqnum INTEGER
)
	 IN SCHD
;


ALTER TABLE snap_terminal 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKterminal ON snap_terminal
(
       port_feature_id                ASC,
       terminal_feature_id            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_terminal
       ADD CONSTRAINT XPKterminal PRIMARY KEY (port_feature_id, 
              terminal_feature_id);


CREATE TABLE snap_timezone (
       feature_id           BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_timezone 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpktimezone ON snap_timezone
(
       feature_id                     ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_timezone
       ADD CONSTRAINT xpktimezone PRIMARY KEY (feature_id);


CREATE TABLE snap_traffic_restriction (
       traffic_restriction_cd CHAR(1) NOT NULL,
       traffic_restriction_meaning VARCHAR(255) NOT NULL,
       traffic_restriction_desc VARCHAR(2000) NOT NULL,
       traffic_restriction_displ_txt VARCHAR(255) NOT NULL,
       allow_connection_constr_ind CHAR(1) NOT NULL,
       direct_flight_usage_ind CHAR(1) NOT NULL,
       allowed_connection_type_cd CHAR(1) NOT NULL,
       traffic_restr_displ_txt_odi BIGINT,
       constr_wthout_bp_op_qual_ind CHAR(1) NOT NULL,
       constr_with_bp_qual_ind CHAR(1) NOT NULL,
       constr_with_op_qual_ind CHAR(1) NOT NULL,
       constr_with_bp_op_qual_ind CHAR(1) NOT NULL,
       chk_mct_orride_flt_trckg_ind CHAR(1) NOT NULL
)
	 IN SCHD
;


ALTER TABLE snap_traffic_restriction 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpktraffic_restric ON snap_traffic_restriction
(
       traffic_restriction_cd         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_traffic_restriction
       ADD CONSTRAINT xpktraffic_restric PRIMARY KEY (
              traffic_restriction_cd);


CREATE TABLE snap_travel_service_location_category (
       location_category_cd CHAR(1) NOT NULL,
       location_category_desc VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_travel_service_location_category 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKtrvl_svc_loc_ca ON snap_travel_service_location_category
(
       location_category_cd           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_travel_service_location_category
       ADD CONSTRAINT XPKtrvl_svc_loc_ca PRIMARY KEY (
              location_category_cd);


CREATE TABLE snap_txt_element (
       txt_element_keyword_id BIGINT NOT NULL,
       txt_element_id       BIGINT NOT NULL,
       txt_element_txt      VARCHAR(4000),
       txt_element_odi      BIGINT,
       owning_rolename      VARCHAR(100) NOT NULL
)
	 IN LANG
;


ALTER TABLE snap_txt_element 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKtxt_element ON snap_txt_element
(
       txt_element_keyword_id         ASC,
       txt_element_id                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_txt_element
       ADD CONSTRAINT XPKtxt_element PRIMARY KEY (
              txt_element_keyword_id, txt_element_id);


CREATE TABLE snap_txt_element_group (
       txt_element_group_id BIGINT NOT NULL,
       txt_element_group_desc VARCHAR(255) NOT NULL,
       owning_rolename      VARCHAR(100) NOT NULL
)
	 IN LANG
;


ALTER TABLE snap_txt_element_group 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKtxt_element_grp ON snap_txt_element_group
(
       txt_element_group_id           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_txt_element_group
       ADD CONSTRAINT XPKtxt_element_grp PRIMARY KEY (
              txt_element_group_id);


CREATE TABLE snap_txt_element_keyword (
       txt_element_keyword_id BIGINT NOT NULL,
       txt_element_type_cd  CHAR(1) NOT NULL,
       txt_element_group_id BIGINT NOT NULL,
       txt_element_keyword_desc VARCHAR(255) NOT NULL,
       owning_rolename      VARCHAR(100) NOT NULL
)
	 IN LANG
;


ALTER TABLE snap_txt_element_keyword 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKtxt_element_kwd ON snap_txt_element_keyword
(
       txt_element_keyword_id         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_txt_element_keyword
       ADD CONSTRAINT XPKtxt_element_kwd PRIMARY KEY (
              txt_element_keyword_id);


CREATE TABLE snap_txt_element_type (
       txt_element_type_cd  CHAR(1) NOT NULL,
       txt_element_type_name VARCHAR(100) NOT NULL,
       max_character_cnt    INTEGER
)
	 IN LANG
;


ALTER TABLE snap_txt_element_type 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKtxt_element_typ ON snap_txt_element_type
(
       txt_element_type_cd            ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_txt_element_type
       ADD CONSTRAINT XPKtxt_element_typ PRIMARY KEY (
              txt_element_type_cd);


CREATE TABLE snap_work_effort (
       work_effort_id       BIGINT NOT NULL,
       work_effort_desc     VARCHAR(255) NOT NULL
)
	 IN REF
;


ALTER TABLE snap_work_effort 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkwork_effort ON snap_work_effort
(
       work_effort_id                 ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_work_effort
       ADD CONSTRAINT xpkwork_effort PRIMARY KEY (work_effort_id);


CREATE TABLE snap_work_effort_std_form (
       work_effort_id       BIGINT NOT NULL,
       std_form_id          BIGINT NOT NULL
)
	 IN REF
;


ALTER TABLE snap_work_effort_std_form 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX xpkwork_effort_sta ON snap_work_effort_std_form
(
       work_effort_id                 ASC,
       std_form_id                    ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE snap_work_effort_std_form
       ADD CONSTRAINT xpkwork_effort_sta PRIMARY KEY (work_effort_id, 
              std_form_id);


CREATE TABLE z_model_database_version (
       generation_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT TIMESTAMP,
       model_version_txt    VARCHAR(255) NOT NULL,
       generation_username  VARCHAR(40) NOT NULL DEFAULT USER
)
	 IN USERSPACE1
;


ALTER TABLE z_model_database_version 
 	 PCTFREE 0 
 ;
CREATE UNIQUE INDEX XPKz_model_db_vsn ON z_model_database_version
(
       generation_timestamp           ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;


ALTER TABLE z_model_database_version
       ADD CONSTRAINT XPKz_model_db_vsn PRIMARY KEY (
              generation_timestamp);




--Set standard permissions for all tables / views

-- ================================================================
-- Script generated via ERwin - Fri Sep 11 10:19:22 2009 - by SCORCORA.
-- From ModelMart://mmart/PhysicalRelease/Snapshot Version 14.0.              
-- ================================================================
--
--
SET CURRENT SCHEMA = 'SNAPSHOT';

--  snap_abbreviation --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_abbreviation TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_abbreviation TO 
	GROUP STAFF;


--  snap_acft_body_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_acft_body_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_acft_body_type TO 
	GROUP STAFF;


--  snap_bank_closure --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_bank_closure TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_bank_closure TO 
	GROUP STAFF;


--  snap_bank_closure_code --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_bank_closure_code TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_bank_closure_code TO 
	GROUP STAFF;


--  snap_bilateral_agreement --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_bilateral_agreement TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_bilateral_agreement TO 
	GROUP STAFF;


--  snap_boardpoint_offpoint --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_boardpoint_offpoint TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_boardpoint_offpoint TO 
	GROUP STAFF;


--  snap_booking_class --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_booking_class TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_booking_class TO 
	GROUP STAFF;
	
--  snap_booking_class_alt --	
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_booking_class_alt TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;
	
GRANT SELECT ON TABLE  snap_booking_class_alt TO 
	GROUP STAFF;

--  snap_cabotage_operating_country --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_cabotage_operating_country TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_cabotage_operating_country TO 
	GROUP STAFF;


--  snap_carrier --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_carrier TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_carrier TO 
	GROUP STAFF;


--  snap_carrier_booking_class --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_carrier_booking_class TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_carrier_booking_class TO 
	GROUP STAFF;


--  snap_carrier_coding --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_carrier_coding TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_carrier_coding TO 
	GROUP STAFF;


--  snap_carrier_default_optional_element --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_carrier_default_optional_element TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_carrier_default_optional_element TO 
	GROUP STAFF;


--  snap_carrier_sched_version --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_carrier_sched_version TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_carrier_sched_version TO 
	GROUP STAFF;


--  snap_carrier_schedule_version_port_default_terminal --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_carrier_schedule_version_port_default_terminal TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_carrier_schedule_version_port_default_terminal TO 
	GROUP STAFF;


--  snap_case --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_case TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_case TO 
	GROUP STAFF;


--  snap_case_comment --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_case_comment TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_case_comment TO 
	GROUP STAFF;


--  snap_case_status_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_case_status_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_case_status_type TO 
	GROUP STAFF;


--  snap_class_type_code --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_class_type_code TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_class_type_code TO 
	GROUP STAFF;


--  snap_coding_scheme --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_coding_scheme TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_coding_scheme TO 
	GROUP STAFF;


--  snap_coding_scheme_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_coding_scheme_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_coding_scheme_category TO 
	GROUP STAFF;


--  snap_coding_scheme_usage --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_coding_scheme_usage TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_coding_scheme_usage TO 
	GROUP STAFF;


--  snap_comm_event --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_comm_event TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_comm_event TO 
	GROUP STAFF;


--  snap_comm_event_purpose --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_comm_event_purpose TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_comm_event_purpose TO 
	GROUP STAFF;


--  snap_comm_event_work_effort --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_comm_event_work_effort TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_comm_event_work_effort TO 
	GROUP STAFF;


--  snap_config_class --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_config_class TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_config_class TO 
	GROUP STAFF;


--  snap_config_combination --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_config_combination TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_config_combination TO 
	GROUP STAFF;


--  snap_contact_method --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_contact_method TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_contact_method TO 
	GROUP STAFF;


--  snap_contact_method_purpose_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_contact_method_purpose_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_contact_method_purpose_type TO 
	GROUP STAFF;


--  snap_contact_method_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_contact_method_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_contact_method_type TO 
	GROUP STAFF;


--  snap_credit_card --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_credit_card TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_credit_card TO 
	GROUP STAFF;


--  snap_credit_card_restriction --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_credit_card_restriction TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_credit_card_restriction TO 
	GROUP STAFF;


--  snap_currency --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_currency TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_currency TO 
	GROUP STAFF;


--  snap_currency_coding --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_currency_coding TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_currency_coding TO 
	GROUP STAFF;


--  snap_default_terminal_equipment_override --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_default_terminal_equipment_override TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_default_terminal_equipment_override TO 
	GROUP STAFF;


--  snap_display_txt_use --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_display_txt_use TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_display_txt_use TO 
	GROUP STAFF;


--  snap_dst_variation --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_dst_variation TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_dst_variation TO 
	GROUP STAFF;


--  snap_equipment_cabin_config --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_cabin_config TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_cabin_config TO 
	GROUP STAFF;


--  snap_equipment_cabin_seat_block_exception --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_cabin_seat_block_exception TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_cabin_seat_block_exception TO 
	GROUP STAFF;


--  snap_equipment_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_category TO 
	GROUP STAFF;


--  snap_equipment_coding --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_coding TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_coding TO 
	GROUP STAFF;
	
	
--  snap_equipment_additional_coding --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_additional_coding TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_additional_coding TO 
	GROUP STAFF;


--  snap_equipment_config --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_config TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_config TO 
	GROUP STAFF;


--  snap_equipment_family --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_family TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_family TO 
	GROUP STAFF;


--  snap_equipment_group --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_group TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_group TO 
	GROUP STAFF;


--  snap_equipment_manufacturer_coding --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_manufacturer_coding TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_manufacturer_coding TO 
	GROUP STAFF;


--  snap_equipment_seat_block_exception_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_seat_block_exception_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_seat_block_exception_type TO 
	GROUP STAFF;


--  snap_equipment_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_type TO 
	GROUP STAFF;


--  snap_equipment_valid_service_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_equipment_valid_service_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_equipment_valid_service_type TO 
	GROUP STAFF;


--  snap_feature --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature TO 
	GROUP STAFF;


--  snap_feature_assoc --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_assoc TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_assoc TO 
	GROUP STAFF;


--  snap_feature_assoc_txt --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_assoc_txt TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_assoc_txt TO 
	GROUP STAFF;


--  snap_feature_assoc_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_assoc_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_assoc_type TO 
	GROUP STAFF;


--  snap_feature_coding --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_coding TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_coding TO 
	GROUP STAFF;


--  snap_feature_currency --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_currency TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_currency TO 
	GROUP STAFF;


--  snap_feature_group --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_group TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_group TO 
	GROUP STAFF;


--  snap_feature_lang --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_lang TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_lang TO 
	GROUP STAFF;


--  snap_feature_txt --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_txt TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_txt TO 
	GROUP STAFF;


--  snap_feature_txt_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_txt_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_txt_type TO 
	GROUP STAFF;


--  snap_feature_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_type TO 
	GROUP STAFF;


--  snap_feature_type_coding --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_type_coding TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_type_coding TO 
	GROUP STAFF;


--  snap_feature_type_coding_assoc --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_type_coding_assoc TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_type_coding_assoc TO 
	GROUP STAFF;


--  snap_feature_version --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_feature_version TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_feature_version TO 
	GROUP STAFF;


--  snap_input_profile_option --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_input_profile_option TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_input_profile_option TO 
	GROUP STAFF;


--  snap_inter_carrier_relationship_txt --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_inter_carrier_relationship_txt TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_inter_carrier_relationship_txt TO 
	GROUP STAFF;


--  snap_interline_agreement_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_interline_agreement_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_interline_agreement_type TO 
	GROUP STAFF;


--  snap_intl_domestic_status --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_intl_domestic_status TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_intl_domestic_status TO 
	GROUP STAFF;


--  snap_iv --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_iv TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_iv TO 
	GROUP STAFF;


--  snap_lang --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_lang TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_lang TO 
	GROUP STAFF;


--  snap_language_coding --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_language_coding TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_language_coding TO 
	GROUP STAFF;


--  snap_leg_booking_class --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_leg_booking_class TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_leg_booking_class TO 
	GROUP STAFF;


--  snap_leg_config_class --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_leg_config_class TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_leg_config_class TO 
	GROUP STAFF;


--  snap_leg_dupl_xref --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_leg_dupl_xref TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_leg_dupl_xref TO 
	GROUP STAFF;


--  snap_leg_opt_element --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_leg_opt_element TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_leg_opt_element TO 
	GROUP STAFF;


--  snap_leg_participant --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_leg_participant TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_leg_participant TO 
	GROUP STAFF;


--  snap_leg_partnership --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_leg_partnership TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_leg_partnership TO 
	GROUP STAFF;


--  snap_leg_seg --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_leg_seg TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_leg_seg TO 
	GROUP STAFF;


--  snap_leg_traffic_rest --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_leg_traffic_rest TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_leg_traffic_rest TO 
	GROUP STAFF;


--  snap_market_sector_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_market_sector_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_market_sector_category TO 
	GROUP STAFF;


--  snap_market_sector_feature_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_market_sector_feature_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_market_sector_feature_category TO 
	GROUP STAFF;


--  snap_market_sector_property --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_market_sector_property TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_market_sector_property TO 
	GROUP STAFF;


--  snap_market_sector_property_valid_value --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_market_sector_property_valid_value TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_market_sector_property_valid_value TO 
	GROUP STAFF;


--  snap_mct_connection_combination --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_mct_connection_combination TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_mct_connection_combination TO 
	GROUP STAFF;


--  snap_mct_rule --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_mct_rule TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_mct_rule TO 
	GROUP STAFF;


--  snap_mct_rule_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_mct_rule_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_mct_rule_type TO 
	GROUP STAFF;


--  snap_meal_service --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_meal_service TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_meal_service TO 
	GROUP STAFF;


--  snap_modifier_action --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_modifier_action TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_modifier_action TO 
	GROUP STAFF;


--  snap_modifier_change_reason --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_modifier_change_reason TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_modifier_change_reason TO 
	GROUP STAFF;


--  snap_odi_option --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_odi_option TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_odi_option TO 
	GROUP STAFF;


--  snap_odi_option_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_odi_option_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_odi_option_category TO 
	GROUP STAFF;


--  snap_oper_purpose_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_oper_purpose_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_oper_purpose_category TO 
	GROUP STAFF;


--  snap_optional_element --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_optional_element TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_optional_element TO 
	GROUP STAFF;


--  snap_optional_element_display_use --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_optional_element_display_use TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_optional_element_display_use TO 
	GROUP STAFF;


--  snap_optional_element_endpoint_use --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_optional_element_endpoint_use TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_optional_element_endpoint_use TO 
	GROUP STAFF;


--  snap_optional_element_pcm_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_optional_element_pcm_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_optional_element_pcm_category TO 
	GROUP STAFF;


--  snap_optional_element_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_optional_element_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_optional_element_type TO 
	GROUP STAFF;


--  snap_optional_element_valid_value --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_optional_element_valid_value TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_optional_element_valid_value TO 
	GROUP STAFF;


--  snap_otp_history --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_otp_history TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_otp_history TO 
	GROUP STAFF;
	
-- snap_otp_history_text
GRANT SELECT ON TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT TO USER SNAPUSR1;
GRANT INSERT ON TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT TO USER SNAPUSR1;
GRANT UPDATE ON TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT TO USER SNAPUSR1;
GRANT DELETE ON TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT TO USER SNAPUSR1;

GRANT SELECT ON TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT TO USER SUNOPSIS;
GRANT INSERT ON TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT TO USER SUNOPSIS;
GRANT UPDATE ON TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT TO USER SUNOPSIS;
GRANT DELETE ON TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT TO USER SUNOPSIS;

GRANT SELECT ON TABLE SNAPSHOT.SNAP_OTP_HISTORY_TEXT TO GROUP STAFF;

--  snap_output_profile_option --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_output_profile_option TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_output_profile_option TO 
	GROUP STAFF;


--  snap_participant_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_participant_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_participant_type TO 
	GROUP STAFF;


--  snap_party --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party TO 
	GROUP STAFF;


--  snap_party_comment --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_comment TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_comment TO 
	GROUP STAFF;


--  snap_party_contact_method --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_contact_method TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_contact_method TO 
	GROUP STAFF;


--  snap_party_contact_method_purpose --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_contact_method_purpose TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_contact_method_purpose TO 
	GROUP STAFF;


--  snap_party_feature_contact_method_purpose --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_feature_contact_method_purpose TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_feature_contact_method_purpose TO 
	GROUP STAFF;


--  snap_party_market_sector --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_market_sector TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_market_sector TO 
	GROUP STAFF;


--  snap_party_market_sector_equipment --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_market_sector_equipment TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_market_sector_equipment TO 
	GROUP STAFF;


--  snap_party_market_sector_feature --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_market_sector_feature TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_market_sector_feature TO 
	GROUP STAFF;


--  snap_party_market_sector_property --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_market_sector_property TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_market_sector_property TO 
	GROUP STAFF;


--  snap_party_relationship --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_relationship TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_relationship TO 
	GROUP STAFF;


--  snap_party_relationship_feature --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_relationship_feature TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_relationship_feature TO 
	GROUP STAFF;


--  snap_party_relationship_member --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_relationship_member TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_relationship_member TO 
	GROUP STAFF;


--  snap_party_role --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role TO 
	GROUP STAFF;


--  snap_party_role_feature --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_feature TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_feature TO 
	GROUP STAFF;


--  snap_party_role_name --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_name TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_name TO 
	GROUP STAFF;


--  snap_party_role_name_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_name_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_name_type TO 
	GROUP STAFF;


--  snap_party_role_note --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_note TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_note TO 
	GROUP STAFF;


--  snap_party_role_note_text --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_note_text TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_note_text TO 
	GROUP STAFF;


--  snap_party_role_note_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_note_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_note_type TO 
	GROUP STAFF;


--  snap_party_role_preferred_contact_method_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_preferred_contact_method_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_preferred_contact_method_type TO 
	GROUP STAFF;


--  snap_party_role_status --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_status TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_status TO 
	GROUP STAFF;


--  snap_party_role_text_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_text_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_text_category TO 
	GROUP STAFF;


--  snap_party_role_text_category_default_rank --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_role_text_category_default_rank TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_role_text_category_default_rank TO 
	GROUP STAFF;


--  snap_party_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_party_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_party_type TO 
	GROUP STAFF;


--  snap_pcm_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_pcm_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_pcm_category TO 
	GROUP STAFF;


--  snap_port --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_port TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_port TO 
	GROUP STAFF;


--  snap_processing_profile --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_processing_profile TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_processing_profile TO 
	GROUP STAFF;


--  snap_processing_profile_comment --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_processing_profile_comment TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_processing_profile_comment TO 
	GROUP STAFF;


--  snap_processing_profile_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_processing_profile_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_processing_profile_type TO 
	GROUP STAFF;


--  snap_product --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_product TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_product TO 
	GROUP STAFF;


--  snap_product_category_code --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_product_category_code TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_product_category_code TO 
	GROUP STAFF;


--  snap_product_override --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_product_override TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_product_override TO 
	GROUP STAFF;


--  snap_product_override_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_product_override_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_product_override_category TO 
	GROUP STAFF;


--  snap_product_party_feature_contact_method --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_product_party_feature_contact_method TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_product_party_feature_contact_method TO 
	GROUP STAFF;


--  snap_product_party_role_text_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_product_party_role_text_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_product_party_role_text_category TO 
	GROUP STAFF;


--  snap_profile_option --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_profile_option TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_profile_option TO 
	GROUP STAFF;


--  snap_profile_option_valid_value --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_profile_option_valid_value TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_profile_option_valid_value TO 
	GROUP STAFF;


--  snap_public_holiday --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_public_holiday TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_public_holiday TO 
	GROUP STAFF;


--  snap_relationship_member_feature_use --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_relationship_member_feature_use TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_relationship_member_feature_use TO 
	GROUP STAFF;


--  snap_relationship_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_relationship_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_relationship_type TO 
	GROUP STAFF;


--  snap_relationship_type_member --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_relationship_type_member TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_relationship_type_member TO 
	GROUP STAFF;


--  snap_restriction_display_use --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_restriction_display_use TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_restriction_display_use TO 
	GROUP STAFF;


--  snap_restriction_validity --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_restriction_validity TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_restriction_validity TO 
	GROUP STAFF;


--  snap_role_family --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_role_family TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_role_family TO 
	GROUP STAFF;


--  snap_role_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_role_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_role_type TO 
	GROUP STAFF;


--  snap_sched_change_stats_detail --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_change_stats_detail TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_change_stats_detail TO 
	GROUP STAFF;


--  snap_sched_change_stats_summary --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_change_stats_summary TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_change_stats_summary TO 
	GROUP STAFF;


--  snap_sched_change_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_change_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_change_type TO 
	GROUP STAFF;


--  snap_sched_default_optional_element --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_default_optional_element TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_default_optional_element TO 
	GROUP STAFF;


--  snap_sched_market_seg --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_market_seg TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_market_seg TO 
	GROUP STAFF;


--  snap_sched_version --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_version TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_version TO 
	GROUP STAFF;


--  snap_sched_version_booking_class --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_version_booking_class TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_version_booking_class TO 
	GROUP STAFF;


--  snap_sched_version_equipment_summary --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_version_equipment_summary TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_version_equipment_summary TO 
	GROUP STAFF;


--  snap_sched_version_name --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_version_name TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_version_name TO 
	GROUP STAFF;


--  snap_sched_version_port_summary --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_sched_version_port_summary TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_sched_version_port_summary TO 
	GROUP STAFF;


--  snap_seg_component --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_seg_component TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_seg_component TO 
	GROUP STAFF;


--  snap_service --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_service TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_service TO 
	GROUP STAFF;


--  snap_service_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_service_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_service_category TO 
	GROUP STAFF;


--  snap_service_haul_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_service_haul_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_service_haul_category TO 
	GROUP STAFF;


--  snap_service_prov_credit_card --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_service_prov_credit_card TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_service_prov_credit_card TO 
	GROUP STAFF;


--  snap_service_prov_credit_card_restriction --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_service_prov_credit_card_restriction TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_service_prov_credit_card_restriction TO 
	GROUP STAFF;


--  snap_service_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_service_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_service_type TO 
	GROUP STAFF;


--  snap_service_type_pcm_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_service_type_pcm_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_service_type_pcm_category TO 
	GROUP STAFF;


--  snap_status_change_reason --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_status_change_reason TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_status_change_reason TO 
	GROUP STAFF;


--  snap_std_form --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_std_form TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_std_form TO 
	GROUP STAFF;


--  snap_terminal --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_terminal TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_terminal TO 
	GROUP STAFF;


--  snap_timezone --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_timezone TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_timezone TO 
	GROUP STAFF;


--  snap_traffic_restriction --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_traffic_restriction TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_traffic_restriction TO 
	GROUP STAFF;


--  snap_travel_service_location_category --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_travel_service_location_category TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_travel_service_location_category TO 
	GROUP STAFF;


--  snap_txt_element --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_txt_element TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_txt_element TO 
	GROUP STAFF;


--  snap_txt_element_group --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_txt_element_group TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_txt_element_group TO 
	GROUP STAFF;


--  snap_txt_element_keyword --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_txt_element_keyword TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_txt_element_keyword TO 
	GROUP STAFF;


--  snap_txt_element_type --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_txt_element_type TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_txt_element_type TO 
	GROUP STAFF;


--  snap_work_effort --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_work_effort TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_work_effort TO 
	GROUP STAFF;


--  snap_work_effort_std_form --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  snap_work_effort_std_form TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  snap_work_effort_std_form TO 
	GROUP STAFF;

-- Multi snap objects

CREATE TABLE SNAPSHOT.SNAP_LOAD_AUDIT (
	SCHED_VERSION_ID BIGINT NOT NULL,
	WORK_ID BIGINT NOT NULL,
	WD_NAME VARCHAR(200) NOT NULL,
	FILE_NAME VARCHAR(200) NOT NULL,
	EXTRACTED INTEGER NOT NULL DEFAULT 0,
	LOADED INTEGER NOT NULL DEFAULT 0,
	COMPLETED INTEGER NOT NULL DEFAULT 0,
	REGISTERED_TIME TIMESTAMP NOT NULL,
	EXTRACTED_TIME TIMESTAMP,
	LOADED_TIME TIMESTAMP,
	COMPLETED_TIME TIMESTAMP
) IN REF;
CREATE UNIQUE INDEX xpksnapaudit ON SNAPSHOT.SNAP_LOAD_AUDIT
(
       SCHED_VERSION_ID        ASC,
       WORK_ID         ASC
)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS 
;
ALTER TABLE SNAPSHOT.SNAP_LOAD_AUDIT
       ADD CONSTRAINT xpksnapaudit PRIMARY KEY (
              SCHED_VERSION_ID, WORK_ID);
              
GRANT INSERT, UPDATE, DELETE, SELECT on SNAPSHOT.SNAP_LOAD_AUDIT to SNAPUSR1, SNAPUSR2, SUNOPSIS, GROUP STAFF;

-- WDF objects
SET CURRENT SCHEMA = 'SNAPSHOT';

(
		LEG_SEG_ID BIGINT,
		IV_ID BIGINT,
		SCHED_VERSION_NAME CHAR(30),
		CARRIER_CD CHAR(3),
		FLIGHT_NUM INTEGER,
		DEP_PORT_CD CHAR(3),
		DEP_CITY_CD CHAR(3),
		DEP_CNTRY_CD CHAR(2),
		ARR_PORT_CD CHAR(3),
		ARR_CITY_CD CHAR(3),
		ARR_CNTRY_CD CHAR(2),
		DEP_TIME_LCL CHAR(4),
		ARR_TIME_LCL CHAR(4),
		ARR_INTERVAL_DAYS VARCHAR(1) WITH DEFAULT '',
		ELAPSED_TIME CHAR(5),
		OPER_DAYS_OF_WEEK CHAR(7),
      	STOPS CHAR(2),
		INTMDTE_PORTS VARCHAR(200) WITH DEFAULT  '',
		ARCRFT_CHANGE_MARKER VARCHAR(1) WITH DEFAULT '',
		GOVT_APPROVAL_MARKER VARCHAR(1) WITH DEFAULT '',
		COMMENT_10_50 VARCHAR(3) WITH DEFAULT '',
		GEN_ARCRFT_TYPE VARCHAR(3) WITH DEFAULT '',
		SPCFC_ARCRFT_TYPE VARCHAR(3) WITH DEFAULT '',
		SERVICE_TYPE_CD VARCHAR(1) WITH DEFAULT '',
		AVL_SEATS VARCHAR(4) WITH DEFAULT '',
		FREIGHT_TONS VARCHAR(7) WITH DEFAULT '',
		PAX_CLASS VARCHAR(30) WITH DEFAULT '',
		FREIGHT_CLASSES VARCHAR(2) WITH DEFAULT '',
		RESTRICTION VARCHAR(4) WITH DEFAULT '',
		DOM_INT_MARKER VARCHAR(2) WITH DEFAULT '',
		LEG_SEG_EFF_START_DATE DATE,
		LEG_SEG_EFF_END_DATE DATE,
		FULL_ROUTING VARCHAR(200) WITH DEFAULT '',
		LONGEST_SECTOR_MARKER VARCHAR(1) WITH DEFAULT '',
		DISTANCE INTEGER,
		SHARED_AIRLINE_DESIGNATOR VARCHAR(3) WITH DEFAULT '',
		ARCRFT_OWNER VARCHAR(3) WITH DEFAULT '',
	    OPERATING_MARKER VARCHAR(1) WITH DEFAULT '',
	    GHOST_FLGHT_MARKER VARCHAR(1) WITH DEFAULT '',
	    DUPLICATE_FLGHT_MARKER VARCHAR(1) WITH DEFAULT '',
	    MEALS VARCHAR(15) WITH DEFAULT '',
		DUP_LEG_XML VARCHAR(4000) WITH DEFAULT '',
		DEP_TERMINAL_CD VARCHAR(2) WITH DEFAULT '',
		ARR_TERMINAL_CD VARCHAR(2) WITH DEFAULT '',
		OPER_PREFIX VARCHAR(1) WITH DEFAULT '',
		OPER_SUFFIX VARCHAR(1) WITH DEFAULT '',
		AVL_SEATS_FIRST VARCHAR(4) WITH DEFAULT  '',
		AVL_SEATS_BUSINESS VARCHAR(4) WITH DEFAULT  '',
		AVL_SEATS_PREM_ECNMY VARCHAR(4) WITH DEFAULT  '',
		AVL_SEATS_ECNMY VARCHAR(4) WITH DEFAULT  '',
		RELEASE_SELL_START_DATE DATE,
		SECURE_FLGHT_MARKER VARCHAR(1) WITH DEFAULT  '',
		IN_FLGHT_SERVICE VARCHAR(300) WITH DEFAULT  '',
		E_TICKETING VARCHAR(2) WITH DEFAULT  '',
		DEP_TIME_UTC CHAR(4),
     	ARR_TIME_UTC CHAR(4),
		UTC_ARR_INTERVAL_DAYS VARCHAR(2) WITH DEFAULT  '',
		UTC_EFF_START_DATE DATE,
		UTC_EFF_END_DATE DATE,
		COCKPIT_CREW_PROVIDER VARCHAR(3) WITH DEFAULT  '',
		CABIN_CREW_PROVIDER VARCHAR(3) WITH DEFAULT  '',
		AUTOMATED_CHECK_IN VARCHAR(1) WITH DEFAULT '',
		ON_TIME_PERFORMANCE_INDICATOR VARCHAR(1) WITH DEFAULT  '',
		ALLIANCE VARCHAR(30) WITH DEFAULT  '',
		CARRIER_CD_ICAO VARCHAR(3) WITH DEFAULT  '',
		DEP_PORT_CD_ICAO VARCHAR(4) WITH DEFAULT  '',
		ARR_PORT_CD_ICAO VARCHAR(4) WITH DEFAULT  '',
		EQUIPMENT_CD_ICAO VARCHAR(4) WITH DEFAULT  '',
		DEP_STATE VARCHAR(2) WITH DEFAULT  '',
		ARR_STATE VARCHAR(2) WITH DEFAULT  '',
		DEP_TIMEDIV_IATA VARCHAR(2) WITH DEFAULT  '',
		ARR_TIMEDIV_IATA VARCHAR(2) WITH DEFAULT  '',
		DEP_TIMEDIV_OAG VARCHAR(2) WITH DEFAULT  '',
		ARR_TIMEDIV_OAG VARCHAR(2) WITH DEFAULT  '',
		OPER_DAYS_OF_WEEK_UTC CHAR(7),
		LAST_IV_UPDATE TIMESTAMP
	);
	
	CREATE INDEX IDX_WDF_MASTER_LEG_SEG_ID
      ON WDF_MASTER_DATA_UNENHANCED (LEG_SEG_ID)
	 PCTFREE 0 
	 ALLOW REVERSE SCANS ;

GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  WDF_MASTER_DATA_UNENHANCED TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

--  z_model_database_version --
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE  z_model_database_version TO
	USER SNAPUSR1, USER SNAPUSR2,
	USER SUNOPSIS, USER MQDB2;

GRANT SELECT ON TABLE  z_model_database_version TO 
	GROUP STAFF;



-- Permissions for all VIEWs




-- Insert a row to 'mark' the version of the model(s) used for the database generation.
INSERT INTO   z_model_database_version 
	(generation_timestamp,
	model_version_txt,
	generation_username)
VALUES 
	(CURRENT TIMESTAMP,
	'*** MODEL: VERSION 14.0 *** Index changes on SNAP_LEG_SEG *** TABLES/ VIEWS '
	|| 'as at Fri Sep 11 10:19:22 2009'
	|| ' - ModelMart://mmart/PhysicalRelease/Snapshot Version 14.0.              ',
	CURRENT USER) ;



