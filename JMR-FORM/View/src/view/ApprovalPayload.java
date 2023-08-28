package view;


import java.text.SimpleDateFormat;

import java.util.TimeZone;

public class ApprovalPayload {
    public ApprovalPayload() {
        super();
    }
   
    
    //bUSINESS
     /***Cloud purpose un comment these section while deploying to TEST cloud **/ 
     public static final String BUDDY_CARE_WSDL = "http://oaosoatst-wls-1.sub08071802371.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/BuddyCareApproval/bpelprocess1_client_ep?WSDL";
    //public static final String DUTY_ALLOWANCE_WSDL ="https://oaosoatest.oandoplc.com/soa-infra/services/default/ExtraDutyAllowanceApproval/allowanceapprovalprocess_client_ep?WSDL";
    public static final String BUDDY_CARE_METHOD = "process";
    
    /***Cloud purpose un comment these section while deploying to TEST cloud **/ 
    public static final String DUTY_ALLOWANCE_WSDL = "http://oaosoatst-wls-1.sub08071802371.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/ExtraDutyAllowanceApproval/allowanceapprovalprocess_client_ep?WSDL";
    //public static final String DUTY_ALLOWANCE_WSDL ="https://oaosoatest.oandoplc.com/soa-infra/services/default/ExtraDutyAllowanceApproval/allowanceapprovalprocess_client_ep?WSDL";
    
    public static final String DUTY_ALLOWANCE_METHOD = "process";
    
    /***Cloud purpose un comment these section while deploying to TEST cloud **/ 
    public static final String UNSUBSCRIBE_WSDL = "http://oaosoatst-wls-1.sub08071802371.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/BuddyCareSubscription/buddyunsubscribebpel_client_ep?WSDL";
    
    public static final String UNSUBSCRIBE_METHOD = "process";
    
    /***Cloud purpose un comment these section while deploying to TEST cloud **/ 
    public static final String JMR_WSDL = "http://oaosoatst-wls-1.sub08071802371.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/JobMobility/jmr_bpel_client_ep?WSDL";
    
    
    /***Cloud purpose un comment these section while deploying to PROD cloud **/ 
  //  public static final String JMR_WSDL = "http://oaosoaprd-wls-1.sub08071802370.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/JobMobility/jmr_bpel_client_ep?WSDL";
    
    
    public static final String JMR_METHOD = "process";
   
    /***Cloud purpose un comment these section while deploying to PROD cloud **/    
   //public static final String DUTY_ALLOWANCE_WSDL ="http://oaosoaprd-wls-1.sub08071802370.oandopaasvcn.oraclevcn.com:9073/soa-infra/services/default/ExtraDutyAllowanceApproval/allowanceapprovalprocess_client_ep?WSDL";
    
    
    public static String businessTypeXMLData(String p_EmployeeName,String p_EmployeeNumber,String p_Email,
                                             String p_BusinessGroup,String p_Cadre,String p_AssignmentType,
                                             String p_JmrRequestId,String p_JmrRequestNo, String p_ApprovalStatus,
                                             String p_Comments ,String p_PersonId, String p_BusinessUnitId,
                                             String p_TransactionDate,String p_DepartmentName,
                                             String p_MobilityType,String p_Nominator,String p_Nominator_mail,String p_Duration,
                                             String p_ProposedStartDate , String p_ProposedEndDate , 
                                             String p_LineManager, String p_NextApprover ,
                                             String p_PreviousApprover , String p_LearningExpectations,
                                             String p_ImpactOnJob , String p_ApproverComments
                                             , String p_Position, String p_MobilityDepartment
                                             , String p_MobilityDeptManager , String p_MobilityDeptManagerEmail)
                                              {
        String[] info=payloadHeader();  
        String totalXml=null;
        String xmlData2="\n";
        System.err.println("Created time===>"+info[0]);
        System.err.println("User===========>"+info[1]);
        System.err.println("Password=======>"+info[2]);
        System.err.println("End time=======>"+info[3]);  

        String xmlData="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:jmr=\"http://xmlns.oracle.com/JobMobilityForm/JobMobility/JMR_BPEL\">\n" + 
        "   <soapenv:Header/>\n" +  
        "   <soapenv:Body>\n" + 
               " <jmr:processRequest>\n" + 
               "         <jmr:EmployeeNo>"+p_EmployeeNumber+"</jmr:EmployeeNo>\n" + 
               "         <jmr:NomineeName>"+p_Nominator+"</jmr:NomineeName>\n" +              
               "         <jmr:EmailAddress>"+p_Nominator_mail+"</jmr:EmailAddress>\n" + 
               "         <jmr:BusinessGroup>"+p_BusinessGroup+"</jmr:BusinessGroup>\n" +               
               "         <jmr:AssignmentType>"+p_AssignmentType+"</jmr:AssignmentType>\n" +
               "         <jmr:JmrRequestId>"+p_JmrRequestId+"</jmr:JmrRequestId>\n" +
               "         <jmr:JmrRequestNo>"+p_JmrRequestNo+"</jmr:JmrRequestNo>\n" +
               "         <jmr:ApprovalStatus>"+p_ApprovalStatus+"</jmr:ApprovalStatus>\n" +
               "         <jmr:Comments>"+p_Comments+"</jmr:Comments>\n" +
               "         <jmr:PersonId>"+p_PersonId+"</jmr:PersonId>\n" +
               "         <jmr:BusinessUnitId>"+p_BusinessUnitId+"</jmr:BusinessUnitId>\n" +       
               "         <jmr:TransactionDate>"+p_TransactionDate+"</jmr:TransactionDate>\n" +
               "         <jmr:DepartmentName>"+p_DepartmentName+"</jmr:DepartmentName>\n" + 
               "         <jmr:MobilityType>"+p_MobilityType+"</jmr:MobilityType>\n" + 
               "         <jmr:Nominator>"+p_EmployeeName+"</jmr:Nominator>\n" + 
               "         <jmr:NominatorMailAddress>"+p_Email+"</jmr:NominatorMailAddress>\n" +
               "         <jmr:Duration>"+p_Duration+"</jmr:Duration>\n" + 
               "         <jmr:ProposedStartDate>"+p_ProposedStartDate+"</jmr:ProposedStartDate>\n" + 
               "         <jmr:ProposedEndDate>"+p_ProposedEndDate+"</jmr:ProposedEndDate>\n" + 
               "         <jmr:LineManager>"+p_LineManager+"</jmr:LineManager>\n" + 
               "         <jmr:NextApprover>"+p_NextApprover+"</jmr:NextApprover>\n" +
               "         <jmr:PreviousApprover>"+p_PreviousApprover+"</jmr:PreviousApprover>\n" +
               "         <jmr:LearningExpectations>"+p_LearningExpectations+"</jmr:LearningExpectations>\n" +
               "         <jmr:ImpactOnJob>"+p_ImpactOnJob+"</jmr:ImpactOnJob>\n" +
               "         <jmr:ApproverComments>"+p_ApproverComments+"</jmr:ApproverComments>\n" +
               "         <jmr:Position>"+p_Position+"</jmr:Position>\n" +
               "         <jmr:MobilityDepartment>"+p_MobilityDepartment+"</jmr:MobilityDepartment>\n" +
               "         <jmr:MobilityDeptManager>"+p_MobilityDeptManager+"</jmr:MobilityDeptManager>\n" +
               "         <jmr:MobilityDeptManagerEmail>"+p_MobilityDeptManagerEmail+"</jmr:MobilityDeptManagerEmail>\n" +
               "         <!--1 or more repetitions:-->\n" ; 
               String xmlData3 =  "      </jmr:processRequest>\n" + 
               "   </soapenv:Body>\n" + 
               "</soapenv:Envelope>";
               totalXml= xmlData+xmlData2+"\n"+xmlData3;
               System.err.println("Totalxml"+totalXml);
               return totalXml;
       
    }


    public static String[] payloadHeader() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'"); //Hours:Minutes:Seconds
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.util.Date expDate;
        expDate = new java.util.Date(t + (10 * 600000000));
        String[] headerInfo = new String[4];
        headerInfo[0] = dateFormat.format(date);
        headerInfo[1] = "oaopdtst";
        headerInfo[2] = "O_0Pst#819";
        headerInfo[3] = dateFormat.format(expDate);
        return headerInfo;
    }
    
    public static String getUser(){        
        return null;
    }   
    
    
//    public static String businessTypeXMLData1(String p_EmployeeName,String p_EmployeeNumber,String p_Email,
//                                             String p_BusinessGroup,String p_Cadre,String p_AssignmentType,
//                                             String p_BuddyCareNo,String p_TransDate, String p_DepartmentName,
//                                             String p_PreviousApprover,String p_NextApprover,
//                                             String p_ApprovalStatus,String p_Comments,String p_ApproverComments,
//                                             ArrayList p_KbcPeriodId , ArrayList p_KnowBuddyCareId , 
//                                             ArrayList p_EffectiveStartDate, ArrayList p_EffectiveEndate ,
//                                             ArrayList p_CurrentAvailablityInd )
//                                              {
//        String[] info=payloadHeader();  
//        String totalXml=null;
//        String xmlData2="\n";
//        System.err.println("Created time===>"+info[0]);
//        System.err.println("User===========>"+info[1]);
//        System.err.println("Password=======>"+info[2]);
//        System.err.println("End time=======>"+info[3]);  
//        String xmlData="<soapenv:Envelope xmlns:bud=\"http://xmlns.oracle.com/BuudyCare_subscription/BuddyCareSubscription/buddyunsubscribebpel\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
//        "   <soapenv:Header/>\n" +  
//        "   <soapenv:Body>\n" + 
//               " <jmr:process>\n" + 
//               "         <bud:EmployeeNo>"+p_EmployeeNumber+"</bud:EmployeeNo>\n" + 
//               "         <bud:EmployeeName>"+p_EmployeeName+"</bud:EmployeeName>\n" +              
//               "         <bud:EmailAddress>"+p_Email+"</bud:EmailAddress>\n" + 
//               "         <bud:BusinessGroup>"+p_BusinessGroup+"</bud:BusinessGroup>\n" + 
//               "         <bud:Cadre>"+p_Cadre+"</bud:Cadre>\n" +
//               "         <bud:AssignmentType>"+p_AssignmentType+"</bud:AssignmentType>\n" +
//               "         <bud:KnowBuddyCareNo>"+p_BuddyCareNo+"</bud:KnowBuddyCareNo>\n" +
//               "         <bud:TransactionDate>"+p_TransDate+"</bud:TransactionDate>\n" +
//               "         <bud:PreviousApprover>"+p_PreviousApprover+"</bud:PreviousApprover>\n" +
//               "         <bud:NextApprover>"+p_NextApprover+"</bud:NextApprover>\n" +
//               "         <bud:DepartmentName>"+p_DepartmentName+"</bud:DepartmentName>\n" +                 
//               "         <bud:Comments>"+p_Comments+"</bud:Comments>\n" +
//               "         <bud:Status>"+p_ApprovalStatus+"</bud:Status>\n" +
//               "         <bud:ApproverComments>"+p_ApproverComments+"</bud:ApproverComments>\n" +
//               "         <bud:SubscriptionStatus>"+p_ApprovalStatus+"</bud:SubscriptionStatus>\n" +
//               "         <!--1 or more repetitions:-->\n" ;  
//        for(int i=0;i<p_KbcPeriodId.size() ;i++){ 
//                    String tempXml=
//                      "         <bud:subscription_elements>\n" + 
//                              "         <bud:KbcPeriodId>"+p_KbcPeriodId.get(i)+"</bud:KbcPeriodId>\n" + 
//                              "         <bud:KnowBuddyCareId>"+p_KnowBuddyCareId.get(i)+"</bud:KnowBuddyCareId>\n" + 
//                              "         <bud:EffectiveStartDate>"+p_EffectiveStartDate.get(i)+"</bud:EffectiveStartDate>\n" + 
//                              "         <bud:EffectiveEndate>"+p_EffectiveEndate.get(i)+"</bud:EffectiveEndate>\n" + 
//                              "         <bud:CurrentAvailablityInd>"+p_CurrentAvailablityInd.get(i)+"</bud:CurrentAvailablityInd>\n" + 
//                      "         </bud:subscription_elements>\n" ;
//                    xmlData2=xmlData2+"\n"+tempXml;
//                          }
//               String xmlData3 =  "      </bud:process>\n" + 
//               "   </soapenv:Body>\n" + 
//               "</soapenv:Envelope>";
//               totalXml= xmlData+xmlData2+"\n"+xmlData3;
//               System.err.println("Totalxml"+totalXml);
//               return totalXml;
//       
//    }
}