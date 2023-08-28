package view;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import model.vo.XxsshrJmrFormVORowImpl;

import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class AddEditJmrForm {
    private String getShortDesc;

    public AddEditJmrForm() {

    }

    int b = 0;

    public void onClickCancel(ActionEvent actionEvent) {
        ViewObject HdrVO = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
        ADFUtils.findOperation("Rollback").execute();
    }

    public void setOrgId() {
        Object obj = ADFContext.getCurrent()
                               .getSessionScope()
                               .get("personId");
        ViewObject empployeeROVO =
            ADFUtils.getApplicationModuleForDataControl("Oando_AMDataControl").findViewObject("Employee_View_ROVO");
        ViewCriteria viewCriteria = empployeeROVO.createViewCriteria();
        ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
        viewCriteriaRow.setAttribute("PersonId", obj);
        viewCriteria.addRow(viewCriteriaRow);
        empployeeROVO.applyViewCriteria(viewCriteria);
        empployeeROVO.executeQuery();
        // System.out.println("LineVo Query ----" + LineVo.getQuery());
        System.out.println("Person Id ----" + obj);

        if (empployeeROVO.getEstimatedRowCount() > 0) {
            Row row = empployeeROVO.first();
            row.getAttribute("BusinessUnitId");
            row.getAttribute("PersonNumber");
            row.getAttribute("FullName");
            row.getAttribute("EmailAddress");
            row.getAttribute("BusinessUnitName");
            //            row.getAttribute("DepartmentName");
            //            System.out.println(row.getAttribute("PersonNumber"));
            //            System.out.println(row.getAttribute("FullName"));
            //            System.out.println(row.getAttribute("EmailAddress"));
            //            System.out.println(row.getAttribute("BusinessUnitName"));
            //            System.out.println(row.getAttribute("DepartmentName"));
            Object orgObj = row.getAttribute("BusinessUnitId");
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("orgId", orgObj);


            System.out.println("111111111");
            ViewObject jmrFormVO =
                ADFUtils.getApplicationModuleForDataControl("Oando_AMDataControl").findViewObject("XxsshrJmrFormVO");
            // Row r = buddyCareVO.first();
            Row newRow = jmrFormVO.createRow();
            newRow.setAttribute("PersonId", obj);
            newRow.setAttribute("EmployeeName", row.getAttribute("FullName"));
            newRow.setAttribute("Number_Trns", row.getAttribute("PersonNumber"));
            newRow.setAttribute("Email_Trns", row.getAttribute("EmailAddress"));
            newRow.setAttribute("Group_Trns", row.getAttribute("BusinessUnitName"));
            newRow.setAttribute("BusinessUnitId", row.getAttribute("BusinessUnitId"));
            newRow.setAttribute("DepartmentName", row.getAttribute("DepartmentName"));
            newRow.setAttribute("LineManager", row.getAttribute("SupervisorName"));
            newRow.setAttribute("Cadre", row.getAttribute("Cadre"));
            jmrFormVO.insertRow(newRow);
        }

    }

    public void setGetShortDesc(String getShortDesc) {
        this.getShortDesc = getShortDesc;
    }

    public String getGetShortDesc() {
        return getShortDesc;
    }

    public void onChangeMobType(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String newValue = "";
        if (valueChangeEvent.getNewValue() != null) {
            newValue = valueChangeEvent.getNewValue().toString();
            System.out.println("newValue ------------ " + newValue);
            if (newValue.equals("Job Rotation")) {
                setGetShortDesc("This is the movement of an employee on a temporary basis to another role within Oando or its affiliates, or an external organization for a specific period. The employee assumes all the duties and responsibilities of their new role for the period of the posting, having handed their own role over to another.");
            } else if (newValue.equals("Job Shadowing")) {
                setGetShortDesc("This is the partial movement of an employee on a temporary basis to another role within Oando or its affiliates, or an external organization for a specific period. This involves the participant working with another employee outside his or her current job role in order to become familiar with a different job through observation and practice. The employee embarking on a job shadowing training will remain active on his/her designated position.");
            } else {
                setGetShortDesc("");
            }
        }

    }

    public String onSubmitAction() {
        boolean result = false;
        boolean value = false;

        ViewObject jmrFormVO1 = ADFUtils.findIterator("XxsshrJmrFormVO1Iterator").getViewObject();
         ViewObject jmrFormVO = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        ViewObject HdrVo = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        System.err.println("after vo1");
        String nom  = jmrFormVO.getCurrentRow().getAttribute("Nominator") == null ? "" :
                      jmrFormVO.getCurrentRow().getAttribute("Nominator").toString();
        RowSetIterator iter = jmrFormVO1.createRowSetIterator(null);
        System.err.println(iter.getRowCount());
        Date currentDate = new Date();
        String nomiCount = HdrVo.getCurrentRow().getAttribute("NominatorCount").toString();
        while (iter.hasNext()) {
            Row r = iter.next();
            Date d = (Date) r.getAttribute("ProposedEndDate");
            if (d.after(currentDate)) {
                String Curr =  r.getAttribute("Nominator").toString();
                    if(Curr.equals(nom))
                b++;
                System.err.println("test");
            }
        }
        if(!"1".equals(nomiCount)){
        if (b > 1 ) {
            JSFUtils.addFacesErrorMessage("After the completion of duration only you can able to submit please save and close!!");
        }else{
            
                

                if (!result) {
                    DCIteratorBinding hdrIter = ADFUtils.findIterator("XxsshrJmrFormVOIterator");
                    XxsshrJmrFormVORowImpl hdrRow = (XxsshrJmrFormVORowImpl) hdrIter.getCurrentRow();
                    hdrRow.setApprovalStatus("SUBMITTED FOR APPROVAL");
                    String xmlData = null;
                    String[] a = null;
                    String JMRWSDL = null;
                    JMRWSDL = ApprovalPayload.JMR_WSDL;

                    String p_EmployeeName =
                        HdrVo.getCurrentRow().getAttribute("EmployeeName") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                    .getAttribute("EmployeeName")
                                                                                                                    .toString();
                    String p_EmployeeNumber =
                        HdrVo.getCurrentRow().getAttribute("Number_Trns") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                     .getAttribute("Number_Trns")
                                                                                                                     .toString();
                    String p_Email = HdrVo.getCurrentRow().getAttribute("Email_Trns") == null ? " " : HdrVo.getCurrentRow()
                                                                                                           .getAttribute("Email_Trns")
                                                                                                           .toString();
                    String p_BusinessGroup =
                        HdrVo.getCurrentRow().getAttribute("Group_Trns") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                   .getAttribute("Group_Trns")
                                                                                                                   .toString();
                    String p_Cadre = HdrVo.getCurrentRow().getAttribute("Cadre") == null ? " " : HdrVo.getCurrentRow()
                                                                                                      .getAttribute("Cadre")
                                                                                                      .toString();

                    String p_AssignmentType =
                        HdrVo.getCurrentRow().getAttribute("Trans_AssignmentType") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                              .getAttribute("Trans_AssignmentType")
                                                                                                                              .toString();


                    String p_JmrRequestId =
                        HdrVo.getCurrentRow().getAttribute("JmrFormId") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                 .getAttribute("JmrFormId")
                                                                                                                 .toString();

                    String p_JmrRequestNo =
                        HdrVo.getCurrentRow().getAttribute("JmrFormNo") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                 .getAttribute("JmrFormNo")
                                                                                                                 .toString();
                    String p_ApprovalStatus =
                        HdrVo.getCurrentRow().getAttribute("ApprovalStatus") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                        .getAttribute("ApprovalStatus")
                                                                                                                        .toString();

                    String p_Comments = HdrVo.getCurrentRow().getAttribute("Comments") == null ? " " : HdrVo.getCurrentRow()
                                                                                                            .getAttribute("Comments")
                                                                                                            .toString();

                    String p_PersonId = HdrVo.getCurrentRow().getAttribute("PersonId") == null ? " " : HdrVo.getCurrentRow()
                                                                                                            .getAttribute("PersonId")
                                                                                                            .toString();
                    String p_BusinessUnitId =
                        HdrVo.getCurrentRow().getAttribute("BusinessUnitId") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                        .getAttribute("BusinessUnitId")
                                                                                                                        .toString();
                    String p_TransactionDate =
                        HdrVo.getCurrentRow().getAttribute("TransactionDate") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                          .getAttribute("TransactionDate")
                                                                                                                          .toString();
                    String p_DepartmentName =
                        HdrVo.getCurrentRow().getAttribute("DepartmentName") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                        .getAttribute("DepartmentName")
                                                                                                                        .toString();
                    String p_MobilityType =
                        HdrVo.getCurrentRow().getAttribute("MobilityType") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                    .getAttribute("MobilityType")
                                                                                                                    .toString();
                    String p_Nominator = HdrVo.getCurrentRow().getAttribute("Nominator") == null ? " " : HdrVo.getCurrentRow()
                                                                                                              .getAttribute("Nominator")
                                                                                                              .toString();
                    String p_Nominator_mail = HdrVo.getCurrentRow().getAttribute("Nomnie_Mail") == null ? " " : HdrVo.getCurrentRow()
                                                                                                              .getAttribute("Nomnie_Mail")
                                                                                                              .toString();
                    String p_Duration = HdrVo.getCurrentRow().getAttribute("Duration") == null ? " " : HdrVo.getCurrentRow()
                                                                                                            .getAttribute("Duration")
                                                                                                            .toString();
                    String p_ProposedStartDate =
                        HdrVo.getCurrentRow().getAttribute("ProposedStartDate") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                              .getAttribute("ProposedStartDate")
                                                                                                                              .toString();
                    String p_ProposedEndDate =
                        HdrVo.getCurrentRow().getAttribute("ProposedEndDate") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                          .getAttribute("ProposedEndDate")
                                                                                                                          .toString();

                    String p_LineManager =
                        HdrVo.getCurrentRow().getAttribute("LineManager") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                  .getAttribute("LineManager")
                                                                                                                  .toString();

                    String p_NextApprover =
                        HdrVo.getCurrentRow().getAttribute("NextApprover") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                    .getAttribute("NextApprover")
                                                                                                                    .toString();
                    String p_PreviousApprover =
                        HdrVo.getCurrentRow().getAttribute("PreviousApprover") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                            .getAttribute("PreviousApprover")
                                                                                                                            .toString();

                    String p_LearningExpectations =
                        HdrVo.getCurrentRow().getAttribute("LearningExpectations") == null ? " " : HdrVo.getCurrentRow()
                                                                                                        .getAttribute("LearningExpectations")
                                                                                                        .toString();

                    String p_ImpactOnJob =
                        HdrVo.getCurrentRow().getAttribute("ImpactOnJob") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                  .getAttribute("ImpactOnJob")
                                                                                                                  .toString();

                    String p_ApproverComments =
                        HdrVo.getCurrentRow().getAttribute("ApproverComments") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                            .getAttribute("ApproverComments")
                                                                                                                            .toString();

                    String p_Position =
                        HdrVo.getCurrentRow().getAttribute("Position_Trns") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                 .getAttribute("Position_Trns")
                                                                                                                 .toString();

                    String p_MobilityDepartment =
                        HdrVo.getCurrentRow().getAttribute("MobilityDepartment") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                                .getAttribute("MobilityDepartment")
                                                                                                                                .toString();

                    String p_MobilityDeptManager =
                        HdrVo.getCurrentRow().getAttribute("MobilityDeptManager") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                                  .getAttribute("MobilityDeptManager")
                                                                                                                                  .toString();

                    String p_MobilityDeptManagerEmail =
                        HdrVo.getCurrentRow().getAttribute("DeptManagerEmailAddr") == null ? " " : HdrVo.getCurrentRow()
                                                                                                        .getAttribute("DeptManagerEmailAddr")
                                                                                                        .toString();

                    xmlData =
                        ApprovalPayload.businessTypeXMLData(p_EmployeeName, p_EmployeeNumber, p_Email, p_BusinessGroup,
                                                            p_Cadre, p_AssignmentType, p_JmrRequestId, p_JmrRequestNo,
                                                            p_ApprovalStatus, p_Comments, p_PersonId, p_BusinessUnitId,
                                                            p_TransactionDate, p_DepartmentName, p_MobilityType,
                                                            p_Nominator,p_Nominator_mail, p_Duration, p_ProposedStartDate, p_ProposedEndDate,
                                                            p_LineManager, p_NextApprover, p_PreviousApprover,
                                                            p_LearningExpectations, p_ImpactOnJob, p_ApproverComments,
                                                            p_Position, p_MobilityDepartment, p_MobilityDeptManager,
                                                            p_MobilityDeptManagerEmail);
                    System.err.println("xmlData =>" + xmlData);
                    a = ApprovalProcess.invokeWsdl(xmlData, JMRWSDL, ApprovalPayload.JMR_METHOD);
                    if (a[0] != null && a[0].equals("True")) {
                        if (hdrRow != null) {
                            OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
                            //  hdrRow.setApprovalStatus("SUBMITTED");
                        }
                        JSFUtils.addFacesInformationMessage("Job Mobility Form Submitted Successfully");
                        value = true;
                    } else {
                        JSFUtils.addFacesInformationMessage("Error");
                        hdrRow.setApprovalStatus("DRAFT");

                    }

                }
                
                
            } }else {
            
       

                if (!result) {
                    DCIteratorBinding hdrIter = ADFUtils.findIterator("XxsshrJmrFormVOIterator");
                    XxsshrJmrFormVORowImpl hdrRow = (XxsshrJmrFormVORowImpl) hdrIter.getCurrentRow();
                    hdrRow.setApprovalStatus("SUBMITTED FOR APPROVAL");
                    String xmlData = null;
                    String[] a = null;
                    String JMRWSDL = null;
                    JMRWSDL = ApprovalPayload.JMR_WSDL;

                    String p_EmployeeName =
                        HdrVo.getCurrentRow().getAttribute("EmployeeName") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                    .getAttribute("EmployeeName")
                                                                                                                    .toString();
                    String p_EmployeeNumber =
                        HdrVo.getCurrentRow().getAttribute("Number_Trns") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                     .getAttribute("Number_Trns")
                                                                                                                     .toString();
                    String p_Email = HdrVo.getCurrentRow().getAttribute("Email_Trns") == null ? " " : HdrVo.getCurrentRow()
                                                                                                           .getAttribute("Email_Trns")
                                                                                                           .toString();
                    String p_BusinessGroup =
                        HdrVo.getCurrentRow().getAttribute("Group_Trns") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                   .getAttribute("Group_Trns")
                                                                                                                   .toString();
                    String p_Cadre = HdrVo.getCurrentRow().getAttribute("Cadre") == null ? " " : HdrVo.getCurrentRow()
                                                                                                      .getAttribute("Cadre")
                                                                                                      .toString();

                    String p_AssignmentType =
                        HdrVo.getCurrentRow().getAttribute("Trans_AssignmentType") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                              .getAttribute("Trans_AssignmentType")
                                                                                                                              .toString();


                    String p_JmrRequestId =
                        HdrVo.getCurrentRow().getAttribute("JmrFormId") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                 .getAttribute("JmrFormId")
                                                                                                                 .toString();

                    String p_JmrRequestNo =
                        HdrVo.getCurrentRow().getAttribute("JmrFormNo") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                 .getAttribute("JmrFormNo")
                                                                                                                 .toString();
                    String p_ApprovalStatus =
                        HdrVo.getCurrentRow().getAttribute("ApprovalStatus") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                        .getAttribute("ApprovalStatus")
                                                                                                                        .toString();

                    String p_Comments = HdrVo.getCurrentRow().getAttribute("Comments") == null ? " " : HdrVo.getCurrentRow()
                                                                                                            .getAttribute("Comments")
                                                                                                            .toString();

                    String p_PersonId = HdrVo.getCurrentRow().getAttribute("PersonId") == null ? " " : HdrVo.getCurrentRow()
                                                                                                            .getAttribute("PersonId")
                                                                                                            .toString();
                    String p_BusinessUnitId =
                        HdrVo.getCurrentRow().getAttribute("BusinessUnitId") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                        .getAttribute("BusinessUnitId")
                                                                                                                        .toString();
                    String p_TransactionDate =
                        HdrVo.getCurrentRow().getAttribute("TransactionDate") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                          .getAttribute("TransactionDate")
                                                                                                                          .toString();
                    String p_DepartmentName =
                        HdrVo.getCurrentRow().getAttribute("DepartmentName") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                        .getAttribute("DepartmentName")
                                                                                                                        .toString();
                    String p_MobilityType =
                        HdrVo.getCurrentRow().getAttribute("MobilityType") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                    .getAttribute("MobilityType")
                                                                                                                    .toString();
                    String p_Nominator = HdrVo.getCurrentRow().getAttribute("Nominator") == null ? " " : HdrVo.getCurrentRow()
                                                                                                              .getAttribute("Nominator")
                                                                                                              .toString();
                  
                    String p_Nominator_mail = HdrVo.getCurrentRow().getAttribute("Nomnie_Mail") == null ? " " : HdrVo.getCurrentRow()
                                                                                                              .getAttribute("Nomnie_Mail")
                                                                                                              .toString();
                    String p_Duration = HdrVo.getCurrentRow().getAttribute("Duration") == null ? " " : HdrVo.getCurrentRow()
                                                                                                            .getAttribute("Duration")
                                                                                                            .toString();
                    String p_ProposedStartDate =
                        HdrVo.getCurrentRow().getAttribute("ProposedStartDate") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                              .getAttribute("ProposedStartDate")
                                                                                                                              .toString();
                    String p_ProposedEndDate =
                        HdrVo.getCurrentRow().getAttribute("ProposedEndDate") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                          .getAttribute("ProposedEndDate")
                                                                                                                          .toString();

                    String p_LineManager =
                        HdrVo.getCurrentRow().getAttribute("LineManager") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                  .getAttribute("LineManager")
                                                                                                                  .toString();

                    String p_NextApprover =
                        HdrVo.getCurrentRow().getAttribute("NextApprover") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                    .getAttribute("NextApprover")
                                                                                                                    .toString();
                    String p_PreviousApprover =
                        HdrVo.getCurrentRow().getAttribute("PreviousApprover") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                            .getAttribute("PreviousApprover")
                                                                                                                            .toString();

                    String p_LearningExpectations =
                        HdrVo.getCurrentRow().getAttribute("LearningExpectations") == null ? " " : HdrVo.getCurrentRow()
                                                                                                        .getAttribute("LearningExpectations")
                                                                                                        .toString();

                    String p_ImpactOnJob =
                        HdrVo.getCurrentRow().getAttribute("ImpactOnJob") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                  .getAttribute("ImpactOnJob")
                                                                                                                  .toString();

                    String p_ApproverComments =
                        HdrVo.getCurrentRow().getAttribute("ApproverComments") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                            .getAttribute("ApproverComments")
                                                                                                                            .toString();

                    String p_Position =
                        HdrVo.getCurrentRow().getAttribute("Position_Trns") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                 .getAttribute("Position_Trns")
                                                                                                                 .toString();

                    String p_MobilityDepartment =
                        HdrVo.getCurrentRow().getAttribute("MobilityDepartment") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                                .getAttribute("MobilityDepartment")
                                                                                                                                .toString();

                    String p_MobilityDeptManager =
                        HdrVo.getCurrentRow().getAttribute("MobilityDeptManager") == null ? " " :
                        HdrVo.getCurrentRow()
                                                                                                                                  .getAttribute("MobilityDeptManager")
                                                                                                                                  .toString();

                    String p_MobilityDeptManagerEmail =
                        HdrVo.getCurrentRow().getAttribute("DeptManagerEmailAddr") == null ? " " : HdrVo.getCurrentRow()
                                                                                                        .getAttribute("DeptManagerEmailAddr")
                                                                                                        .toString();

                    xmlData =
                        ApprovalPayload.businessTypeXMLData(p_EmployeeName, p_EmployeeNumber, p_Email, p_BusinessGroup,
                                                            p_Cadre, p_AssignmentType, p_JmrRequestId, p_JmrRequestNo,
                                                            p_ApprovalStatus, p_Comments, p_PersonId, p_BusinessUnitId,
                                                            p_TransactionDate, p_DepartmentName, p_MobilityType,
                                                            p_Nominator,p_Nominator_mail,p_Duration, p_ProposedStartDate, p_ProposedEndDate,
                                                            p_LineManager, p_NextApprover, p_PreviousApprover,
                                                            p_LearningExpectations, p_ImpactOnJob, p_ApproverComments,
                                                            p_Position, p_MobilityDepartment, p_MobilityDeptManager,
                                                            p_MobilityDeptManagerEmail);
                    System.err.println("xmlData =>" + xmlData);
                    a = ApprovalProcess.invokeWsdl(xmlData, JMRWSDL, ApprovalPayload.JMR_METHOD);
                    if (a[0] != null && a[0].equals("True")) {
                        if (hdrRow != null) {
                            OperationBinding binding = (OperationBinding) ADFUtils.findOperation("Commit").execute();
                            //  hdrRow.setApprovalStatus("SUBMITTED");
                        }
                        JSFUtils.addFacesInformationMessage("Job Mobility Form Submitted Successfully");
                        value = true;
                    } else {
                        JSFUtils.addFacesInformationMessage("Error");
                        hdrRow.setApprovalStatus("DRAFT");

                    }

                }
            
        }

        
        if (value) {
            HdrVo.applyViewCriteria(null);
            HdrVo.executeQuery();
            return "back";
        } else {
            return null;

        }

        // return null;
    }

    public void onChangeofStartDate(ValueChangeEvent valueChangeEvent) {
        Date start = (Date) valueChangeEvent.getNewValue();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ViewObject jmrFormVO = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        Row currentRow = jmrFormVO.getCurrentRow();
        String currentDuration = "";
        String endDatee = "";
        int addMonths = 0;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat formatterr = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();
        Date stdate = (Date) currentRow.getAttribute("ProposedStartDate");
        if (currentDate.after(stdate)) {
            JSFUtils.addFacesErrorMessage("Proposed start should be greater than current date");
            jmrFormVO.getCurrentRow().setAttribute("ProposedStartDate", null);
            jmrFormVO.getCurrentRow().setAttribute("ProposedEndDate", null);
        }


        // System.err.println("TransactionDate"+currentRow.getAttribute("TransactionDate"));
        // if(currentRow.getAttribute("TransactionDate")  currentRow.getAttribute("ProposedStartDate")){}
        if (currentRow != null && currentRow.getAttribute("Duration") != null) {
            if (currentDuration != null) {
                currentDuration = currentRow.getAttribute("Duration") == null ? "" : currentRow.getAttribute("Duration").toString();
                if (currentDuration.equals("3 months")) {
                    addMonths = 3;
                } else if (currentDuration.equals("6 months")) {
                    addMonths = 6;
                } else if (currentDuration.equals("1 month")) {
                    addMonths = 1;
                } else if (currentDuration.equals("2 months")) {
                    addMonths = 2;
                } else if (currentDuration.equals("4 months")) {
                    addMonths = 4;
                } else if (currentDuration.equals("5 months")) {
                    addMonths = 5;
                } else {
                    addMonths = 0;
                }

                // Date stdate = (Date) currentRow.getAttribute("ProposedStartDate");
                System.out.println("stdate----------- " + stdate);
                calendar.setTime(stdate);
                calendar.add(Calendar.MONTH, addMonths);
                Date endDate = calendar.getTime();
                // endDatee = formatter.format(endDate);
                System.out.println("endDate----------" + endDate);
                //System.out.println("endDatee------- " + endDatee);
                //endDateValue.setValue(endDate);
                currentRow.setAttribute("ProposedEndDate", endDate);
                Date ennddate = (Date) currentRow.getAttribute("ProposedEndDate");
                System.out.println("ennddate========= " + ennddate);
            }
        } else {
            JSFUtils.addFacesErrorMessage("Duration is Required");
            currentRow.setAttribute("ProposedStartDate", "");
        }

        ViewObject jmrFormVO1 = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        System.err.println("after vo1");
        RowSetIterator iter = jmrFormVO1.createRowSetIterator(null);
        System.err.println(iter.getRowCount());
        int a = 0;
        String nom = jmrFormVO1.getCurrentRow().getAttribute("Nominator") == null ? " " :
                     jmrFormVO1.getCurrentRow().getAttribute("Nominator").toString();
        while (iter.hasNext()) {
            Row r = iter.next();
            System.err.println(r.getAttribute("ProposedEndDate"));

            Date d = (Date) r.getAttribute("ProposedEndDate");
            if (d.after(start)) {
                //get nomini
                String Curr =  r.getAttribute("Nominator")== null ? "" : r.getAttribute("Nominator").toString();
                if(Curr.equals(nom)){
                    a++;
                }
            }

            System.err.println("a" + a);

            System.err.println("b" + b);
        }
        String nomiCount = jmrFormVO.getCurrentRow().getAttribute("NominatorCount") == null ? "" : 
                           jmrFormVO.getCurrentRow().getAttribute("NominatorCount").toString();
   if( !"1".equals(nomiCount)){
        if (a > 1  && !"1".equals(nomiCount)) {
            JSFUtils.addFacesErrorMessage("Already you have plan in this time !!");
            jmrFormVO.getCurrentRow().setAttribute("ProposedStartDate", null);
            jmrFormVO.getCurrentRow().setAttribute("ProposedEndDate", null);
            //ADFUtils.findOperation("Commit").execute();

        }
   }
       }


    public String onClickSaveAction() {
        System.out.println("Inside save");
        ViewObject vo = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        XxsshrJmrFormVORowImpl hdrRow = (XxsshrJmrFormVORowImpl) vo.getCurrentRow();
        if (hdrRow != null) {
            hdrRow.setApprovalStatus("DRAFT");
        }
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Job Mobility Form Saved Successfully");
        //vo.applyViewCriteria(null);
        //svo.executeQuery();
        return null;
    }

    public void onChangeOfDept(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String newValue = "";
        String deptManager = "";
        String deptManagerEmail = "";
        ViewObject jmrFormVO = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        Row currentRow = jmrFormVO.getCurrentRow();
        if (valueChangeEvent.getNewValue() != null) {
            newValue = valueChangeEvent.getNewValue().toString();
            System.out.println("newValue ------------ " + newValue);
            ViewObject mobDeptROVO =
                ADFUtils.getApplicationModuleForDataControl("Oando_AMDataControl")
                .findViewObject("MobilityDeptManagers_ROVO");
            ViewCriteria viewCriteria = mobDeptROVO.createViewCriteria();
            ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
            viewCriteriaRow.setAttribute("Name", newValue);
            viewCriteria.addRow(viewCriteriaRow);
            mobDeptROVO.applyViewCriteria(viewCriteria);
            mobDeptROVO.executeQuery();
            System.out.println("Count--------- " + mobDeptROVO.getEstimatedRowCount());
            if (mobDeptROVO.getEstimatedRowCount() > 0) {
                Row row = mobDeptROVO.first();
                deptManager = row.getAttribute("DepartmentHead").toString();
                deptManagerEmail = row.getAttribute("EmailAddress").toString();
                System.out.println("deptManager------- " + deptManager);
                currentRow.setAttribute("MobilityDeptManager", deptManager);
                currentRow.setAttribute("DeptManagerEmailAddr", deptManagerEmail);
            } else {
                currentRow.setAttribute("MobilityDeptManager", "");
                currentRow.setAttribute("DeptManagerEmailAddr", "");
            }

        }
    }

    public void onChangeDuration(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ViewObject jmrFormVO = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        jmrFormVO.getCurrentRow().setAttribute("ProposedStartDate", null);
        jmrFormVO.getCurrentRow().setAttribute("ProposedEndDate", null);
    }


    public void onClickNominatorVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ViewObject jmrFormVO = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        jmrFormVO.getCurrentRow().setAttribute("ProposedStartDate", null);
        jmrFormVO.getCurrentRow().setAttribute("ProposedEndDate", null);
    }
}
