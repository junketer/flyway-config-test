SELECT
  carrier_cd                                      AS carrier,
  service_num_str                                 AS fltno,
  dep_time_lcl                                    AS deptim,
  arr_time_lcl                                    AS arrtim,
  oper_days_of_week                               AS days,
  service_type_cd                                 AS service,
  spcfc_arcrft_type                               AS inpacft,
  date_format(leg_seg_eff_start_date, 'yyyyMMdd') AS efffrom,
  date_format(leg_seg_eff_end_date, 'yyyyMMdd')   AS effto,
  full_routing                                    AS routing,
  operating_marker                                AS operating,
  shared_airline_designator                       AS sad,
  dep_port_cd                                     AS depapt,
  arr_port_cd                                     AS arrapt,
  rtrim(cast(stops AS STRING))                    AS stops
FROM broward_filtered
order by dep_port_cd, arr_port_cd, dep_time_lcl, arr_time_lcl, carrier_cd, service_num