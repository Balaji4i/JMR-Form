package model.vo;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 30 14:45:32 IST 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxsshrJmrFormVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public XxsshrJmrFormVOImpl() {
    }

    /**
     * Returns the bind variable value for b_username.
     * @return bind variable value for b_username
     */
    public String getb_username() {
        return (String) getNamedWhereClauseParam("b_username");
    }

    /**
     * Sets <code>value</code> for bind variable b_username.
     * @param value value to bind as b_username
     */
    public void setb_username(String value) {
        setNamedWhereClauseParam("b_username", value);
    }
}

