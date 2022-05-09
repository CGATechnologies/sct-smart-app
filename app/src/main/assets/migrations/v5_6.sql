DROP VIEW IF EXISTS SessionView;
CREATE VIEW SessionView
AS
select p.*
,(
    select coalesce(count(householdId), 0)
    from households
    where sessionId = p.id and selection = 'Selected'
) selectedHouseholds
from pev_sessions p
;