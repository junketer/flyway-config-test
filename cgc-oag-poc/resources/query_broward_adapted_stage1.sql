SELECT
  carrier_cd                                      ,
  service_num_str                                 ,
  dep_time_lcl                                    ,
  arr_time_lcl                                    ,
  oper_days_of_week                               ,
  service_type_cd                                 ,
  spcfc_arcrft_type                               ,
  leg_seg_eff_start_date                          ,
  leg_seg_eff_end_date                            ,
  full_routing                                    ,
  operating_marker                                ,
  shared_airline_designator                       ,
  dep_port_cd                                     ,
  arr_port_cd                                     ,
  stops                                           ,
  service_num
FROM snap_unenhanced
WHERE
  NOT (carrier_cd IN ('5X', 'FX', 'QB', 'XB', 'FR', 'G4'))
  AND sched_version_name IN ('Schedules', 'Cargo Duplicate')
  AND service_type_cd IN ('C', 'G', 'J', 'Q', 'S')
  AND (dep_port_cd = 'FLL' OR arr_port_cd = 'FLL')
  AND leg_seg_eff_end_date >= date_add('2017-11-1', 0)
  AND leg_seg_eff_start_date <= add_months('2017-11-1', 3)