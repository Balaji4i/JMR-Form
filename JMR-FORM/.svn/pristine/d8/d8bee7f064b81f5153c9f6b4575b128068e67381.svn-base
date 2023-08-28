package view;

import com.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class SearchJmrForm {
    public SearchJmrForm() {

    }

    public void onClickDelete(ActionEvent actionEvent) {
        ADFUtils.findOperation("Delete").execute();
        ADFUtils.findOperation("Commit").execute();
    }

    public void onClickEdit(ActionEvent actionEvent) {
        Object obj = ADFContext.getCurrent()
                               .getPageFlowScope()
                               .get("headerId");
        System.err.println("Object Name" + obj);
        ViewObject HdrVO = ADFUtils.findIterator("XxsshrJmrFormVOIterator").getViewObject();
        ViewCriteria hdrVC = HdrVO.createViewCriteria();
        ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
        hdrVcr.setAttribute("JmrFormId", obj);
        hdrVC.addRow(hdrVcr);
        HdrVO.applyViewCriteria(hdrVC);
        HdrVO.executeQuery();
    }
}
