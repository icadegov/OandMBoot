package in.OAndM.mappers;


import in.OAndM.Entities.RtiProformaG;
import in.OAndM.DTO.RtiProformaGDto;
import in.OAndM.core.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RtiProformaGMapper implements BaseMapper<RtiProformaG, RtiProformaGDto> {

    @Override
    public RtiProformaGDto mapEntityToModel(RtiProformaG entity) {
        if (entity == null) {
            return null;
        }

        RtiProformaGDto dto = new RtiProformaGDto();
        dto.setProGId(entity.getProGId());
        dto.setAppealNum(entity.getAppealNo());
        dto.setAppealDate(entity.getAppealDate());
        dto.setNameOfAppellant(entity.getNameOfAppellant());
        dto.setAppellantAddress(entity.getAppellantAddress());
        dto.setAppealReceiptDate(entity.getAppealReceiptDate());
        dto.setPioName(entity.getPioName());
        dto.setPioDesignation(entity.getPioDesignation());
        dto.setAppnNum(entity.getApplicationNo());
        dto.setAppnDate(entity.getApplicationDate());
        dto.setAppellateAuthorityName(entity.getNameOfAppellate());
        dto.setAppellateAuthorityAddre(entity.getAppellateAddress());
        dto.setAppellateFirstDecisionDate(entity.getAppellateFirstDecisionDate());
        dto.setAppellateFirstDecisionAllowRejec(entity.getAppellateFirstDecisionAllowRejec());
        //dto.setRejectsectionid(entity.getAppellateFirstDecisionRejSection());
        //System.out.println("section status is null1");
        if (entity.getRejectionSectionStatus() == null || entity.getRejectionSectionStatus().getRejectSectionId() ==0) {
        	//System.out.println("section status is null");
        	dto.setRejectionSectionStatus(null);
        }

        if (entity.getRejectionSectionStatus() != null && entity.getRejectionSectionStatus().getRejectSectionId() >0 ) {
            dto.setRejectSectionId(entity.getRejectionSectionStatus().getRejectSectionId());
            dto.setRtiRejectionSection(entity.getRejectionSectionStatus().getRtiRejectionSection());
        }
        dto.setChargeForInfo(entity.getChargesCollectForFurnish());
        dto.setSecondAppealMade(entity.getSecondAppealMade193());
        dto.setSecondAppealNoticeNum(entity.getSecondAppealNoticeNum());
        dto.setSecondAppealNoticeDate(entity.getSecondAppealNoticeDate());
        dto.setSecondAppealHearingDate(entity.getSecondAppealHearingDate());
        dto.setRemarks(entity.getRemarks());
        dto.setUnit(entity.getUnit());
        dto.setCircle(entity.getCircle());
        dto.setDivision(entity.getDivision());
        dto.setSubDivision(entity.getSubDivision());
        dto.setPost(entity.getPost());
        dto.setDeleteFlag(entity.getDeleteFlag());
        dto.setIsLatest(entity.getIsLatest());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedTime(entity.getCreatedTime());
        dto.setDeletedBy(entity.getDeletedBy());
        dto.setDeletedTime(entity.getDeletedTime());
        dto.setEditedBy(entity.getEditedBy());
        dto.setEditedTime(entity.getEditedTime());
        dto.setApplicationFee(entity.getApplicationFee());

        return dto;
    }

    @Override
    public RtiProformaG mapModelToEntity(RtiProformaGDto model) {
        if (model == null) {
            return null;
        }

        RtiProformaG entity = new RtiProformaG();
        entity.setProGId(model.getProGId());
        entity.setAppealNo(model.getAppealNum());
        entity.setAppealDate(model.getAppealDate());
        entity.setNameOfAppellant(model.getNameOfAppellant());
        entity.setAppellantAddress(model.getAppellantAddress());
        entity.setAppealReceiptDate(model.getAppealReceiptDate());
        entity.setPioName(model.getPioName());
        entity.setPioDesignation(model.getPioDesignation());
        entity.setApplicationNo(model.getAppnNum());
        entity.setApplicationDate(model.getAppnDate());
        entity.setNameOfAppellate(model.getAppellateAuthorityName());
        entity.setAppellateAddress(model.getAppellateAuthorityAddre());
        entity.setAppellateFirstDecisionDate(model.getAppellateFirstDecisionDate());
        entity.setAppellateFirstDecisionAllowRejec(model.getAppellateFirstDecisionAllowRejec());
        entity.setAppellateFirstDecisionRejSection(model.getRejectSectionId());       
//        if (model.getRejectsectionid() != null) {
//        	RtiRejectionStatus rejectionStatus = new RtiRejectionStatus();
//        	 rejectionStatus.setRejectSectionId(model.getRejectsectionid());
//        	 entity.setRejectionSectionStatus(rejectionStatus);
//        }
        if (model.getAppellateFirstDecisionAllowRejec() == null) {
           // System.out.println("Reject Section ID is null, assigning default value 0.");
        }
        entity.setChargesCollectForFurnish(model.getChargeForInfo());
        entity.setSecondAppealMade193(model.getSecondAppealMade());
        entity.setSecondAppealNoticeNum(model.getSecondAppealNoticeNum());
        entity.setSecondAppealNoticeDate(model.getSecondAppealNoticeDate());
        entity.setSecondAppealHearingDate(model.getSecondAppealHearingDate());
        entity.setRemarks(model.getRemarks());
        if (model.getUser() != null) {
            //System.out.println("user not null in G Dto1");
            entity.setUnit(model.getUser().getUnitId());
            entity.setCircle(model.getUser().getCircleId());
            entity.setDivision(model.getUser().getDivisionId());
            entity.setSubDivision(model.getUser().getSubDivisionId());
            entity.setPost(model.getUser().getPostId());
            entity.setCreatedBy(model.getUser().getUsername());
        }
        if (model.getUser() == null) {
        entity.setUnit(model.getUnit());
        entity.setCircle(model.getCircle());
        entity.setDivision(model.getDivision());
        entity.setSubDivision(model.getSubDivision());
        entity.setPost(model.getPost());
        entity.setCreatedBy(model.getUser().getUsername());
        }
        entity.setDeleteFlag(model.getDeleteFlag());
        entity.setIsLatest(model.getIsLatest());
        
        entity.setCreatedTime(model.getCreatedTime());
        entity.setDeletedBy(model.getDeletedBy());
        entity.setDeletedTime(model.getDeletedTime());
        entity.setEditedBy(model.getEditedBy());
        entity.setEditedTime(model.getEditedTime());
        entity.setApplicationFee(model.getApplicationFee());

        return entity;
    }

    @Override
    public List<RtiProformaGDto> mapEntityToModel(List<RtiProformaG> entities) {
        return entities.stream()
                .map(this::mapEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<RtiProformaG> mapModelToEntity(List<RtiProformaGDto> models) {
        return models.stream()
                .map(this::mapModelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void mapModelToEntity(RtiProformaG entity, RtiProformaGDto model) {
        if (entity == null || model == null) {
            return;
        }

        entity.setProGId(model.getProGId());
        entity.setAppealNo(model.getAppealNum());
        entity.setAppealDate(model.getAppealDate());
        entity.setNameOfAppellant(model.getNameOfAppellant());
        entity.setAppellantAddress(model.getAppellantAddress());
        entity.setAppealReceiptDate(model.getAppealReceiptDate());
        entity.setPioName(model.getPioName());
        entity.setPioDesignation(model.getPioDesignation());
        entity.setApplicationNo(model.getAppnNum());
        entity.setApplicationDate(model.getAppnDate());
        entity.setNameOfAppellate(model.getAppellateAuthorityName());
        entity.setAppellateAddress(model.getAppellateAuthorityAddre());
        entity.setAppellateFirstDecisionDate(model.getAppellateFirstDecisionDate());
        entity.setAppellateFirstDecisionAllowRejec(model.getAppellateFirstDecisionAllowRejec());
        entity.setAppellateFirstDecisionRejSection(model.getRejectSectionId());
        entity.setChargesCollectForFurnish(model.getChargeForInfo());
        entity.setSecondAppealMade193(model.getSecondAppealMade());
        entity.setSecondAppealNoticeNum(model.getSecondAppealNoticeNum());
        entity.setSecondAppealNoticeDate(model.getSecondAppealNoticeDate());
        entity.setSecondAppealHearingDate(model.getSecondAppealHearingDate());
        entity.setRemarks(model.getRemarks());
        if (model.getUser() != null) {
           // System.out.println("user not null in G Dto2");
            entity.setUnit(model.getUser().getUnitId());
            entity.setCircle(model.getUser().getCircleId());
            entity.setDivision(model.getUser().getDivisionId());
            entity.setSubDivision(model.getUser().getSubDivisionId());
            entity.setPost(model.getUser().getPostId());
            entity.setCreatedBy(model.getUser().getUsername());
        }
                if (model.getUser() == null) {
            entity.setUnit(model.getUnit());
            entity.setCircle(model.getCircle());
            entity.setDivision(model.getDivision());
            entity.setSubDivision(model.getSubDivision());
            entity.setPost(model.getPost());
            entity.setCreatedBy(model.getUser().getUsername());
            }
        entity.setDeleteFlag(model.getDeleteFlag());
        entity.setIsLatest(model.getIsLatest());
       
        entity.setCreatedTime(model.getCreatedTime());
        entity.setDeletedBy(model.getDeletedBy());
        entity.setDeletedTime(model.getDeletedTime());
        entity.setEditedBy(model.getEditedBy());
        entity.setEditedTime(model.getEditedTime());
        entity.setApplicationFee(model.getApplicationFee());
    }

    @Override
    public void mapEntityToModel(RtiProformaG entity, RtiProformaGDto model) {
        if (entity == null || model == null) {
            return;
        }

        model.setProGId(entity.getProGId());
        model.setAppealNum(entity.getAppealNo());
        model.setAppealDate(entity.getAppealDate());
        model.setNameOfAppellant(entity.getNameOfAppellant());
        model.setAppellantAddress(entity.getAppellantAddress());
        model.setAppealReceiptDate(entity.getAppealReceiptDate());
        model.setPioName(entity.getPioName());
        model.setPioDesignation(entity.getPioDesignation());
        model.setAppnNum(entity.getApplicationNo());
        model.setAppnDate(entity.getApplicationDate());
        model.setAppellateAuthorityName(entity.getNameOfAppellate());
        model.setAppellateAuthorityAddre(entity.getAppellateAddress());
        //model.setAppellatefirstdecisiondate(entity.getAppellateFirstDecisionDate());
        model.setAppellateFirstDecisionAllowRejec(entity.getAppellateFirstDecisionAllowRejec());
        //model.setRejectsectionid(entity.getAppellateFirstDecisionRejSection());
       // System.out.println("section status is null2");
        if (entity.getRejectionSectionStatus() == null || entity.getRejectionSectionStatus().getRejectSectionId() ==0) {
        	//System.out.println("section status is null");
        	model.setRejectionSectionStatus(null);
        }

        if (entity.getRejectionSectionStatus() != null && entity.getRejectionSectionStatus().getRejectSectionId() >0 ) {
            model.setRejectSectionId(entity.getRejectionSectionStatus().getRejectSectionId());
            model.setRtiRejectionSection(entity.getRejectionSectionStatus().getRtiRejectionSection());
        }
        model.setChargeForInfo(entity.getChargesCollectForFurnish());
        model.setSecondAppealMade(entity.getSecondAppealMade193());
        model.setSecondAppealNoticeNum(entity.getSecondAppealNoticeNum());
        model.setSecondAppealNoticeDate(entity.getSecondAppealNoticeDate());
        model.setSecondAppealHearingDate(entity.getSecondAppealHearingDate());
        model.setRemarks(entity.getRemarks());
        model.setUnit(entity.getUnit());
        model.setCircle(entity.getCircle());
        model.setDivision(entity.getDivision());
        model.setSubDivision(entity.getSubDivision());
        model.setPost(entity.getPost());
        model.setDeleteFlag(entity.getDeleteFlag());
        model.setIsLatest(entity.getIsLatest());
        model.setCreatedBy(entity.getCreatedBy());
        model.setCreatedTime(entity.getCreatedTime());
        model.setDeletedBy(entity.getDeletedBy());
        model.setDeletedTime(entity.getDeletedTime());
        model.setEditedBy(entity.getEditedBy());
        model.setEditedTime(entity.getEditedTime());
        model.setApplicationFee(entity.getApplicationFee());
    }
}

