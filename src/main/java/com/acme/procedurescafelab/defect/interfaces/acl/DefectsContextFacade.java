package com.acme.procedurescafelab.defect.interfaces.acl;

import com.acme.procedurescafelab.defect.domain.model.commands.CreateDefectCommand;

public interface DefectsContextFacade {

    Long createDefect(CreateDefectCommand command);
}
