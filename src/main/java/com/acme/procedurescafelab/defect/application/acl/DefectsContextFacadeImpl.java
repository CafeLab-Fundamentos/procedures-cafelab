package com.acme.procedurescafelab.defect.application.acl;

import com.acme.procedurescafelab.defect.domain.model.commands.CreateDefectCommand;
import com.acme.procedurescafelab.defect.domain.services.DefectCommandService;
import com.acme.procedurescafelab.defect.interfaces.acl.DefectsContextFacade;
import org.springframework.stereotype.Service;

@Service
public class DefectsContextFacadeImpl implements DefectsContextFacade {
    private final DefectCommandService defectCommandService;

    public DefectsContextFacadeImpl(DefectCommandService defectCommandService) {
        this.defectCommandService = defectCommandService;
    }

    @Override
    public Long createDefect(CreateDefectCommand command) {
        var defect = defectCommandService.handle(command);
        return defect.isEmpty() ? 0L : defect.get().getId();
    }
}
