--------------------------------------------------------
-- Initiale Datenbankbefuellung Szenario Send Fetch
-- Oracle Database.
--------------------------------------------------------

Insert into MANDATOR (ID, NAME) Values (seq_mandator_id.nextval, 'TEST');
   
Insert into PROCEDURE_TYPE (ID, NAME)
 Values (seq_procedure_type_id.nextval, 'SCENARIO_SEND_FETCH');
   
Insert into PROCEDURE_PHASE_CONFIGURATION (ID, PHASE, PROCEDURE_TYPE_ID)
 Values
   (seq_procephase_config_id.nextval,
    'PHASE3', 
    (select id from procedure_type where name = 'SCENARIO_SEND_FETCH') );
   
Insert into PROCEDURE_PHASE_CONFIGURATION
   (ID, PHASE, PROCEDURE_TYPE_ID, NEXT_PHASE_CONFIGURATION_ID)
 Values
   (seq_procephase_config_id.nextval,
    'PHASE2', 
    (select id from procedure_type where name = 'SCENARIO_SEND_FETCH'),
     (select id from PROCEDURE_PHASE_CONFIGURATION 
         where PROCEDURE_PHASE_CONFIGURATION.PROCEDURE_TYPE_ID =
        (select id from PROCEDURE_TYPE where PROCEDURE_TYPE.NAME = 'SCENARIO_SEND_FETCH')
        and   PROCEDURE_PHASE_CONFIGURATION.PHASE = 'PHASE3'  ));
     
Insert into PROCEDURE_PHASE_CONFIGURATION
   (ID, PHASE, PROCEDURE_TYPE_ID, NEXT_PHASE_CONFIGURATION_ID)
 Values
   (seq_procephase_config_id.nextval,
    'PHASE1', 
    (select id from procedure_type where name = 'SCENARIO_SEND_FETCH'),
     (select id from PROCEDURE_PHASE_CONFIGURATION 
         where PROCEDURE_PHASE_CONFIGURATION.PROCEDURE_TYPE_ID =
        (select id from PROCEDURE_TYPE where PROCEDURE_TYPE.NAME = 'SCENARIO_SEND_FETCH')
        and   PROCEDURE_PHASE_CONFIGURATION.PHASE = 'PHASE2'  ));


Insert into PROCEDURE
   (ID, NAME, SHORT_KEY, MANDATOR_ID, PROCEDURE_TYPE_ID)
 Values
   (seq_procedure_id.nextval, 'Datenabgleich', 'SEND_FETCH', 
   (select id from MANDATOR where name = 'TEST'),
    (select id from PROCEDURE_TYPE where name = 'SCENARIO_SEND_FETCH'));