package com.provizit.dnp.Utilities;

import com.provizit.dnp.Services.AutoShutDownModel;
import com.provizit.dnp.Services.CategoryData;

import java.io.Serializable;
import java.util.ArrayList;

public class CompanyData implements Serializable {
    private RoleDetails roleDetails;
    CommonObject hierarchys;
    private EmpData empData;
    private String android;
    private String emp_id, email, mobile, mobilecode, entrypoint, meetingroom;
    private String comp_id;
    private String location;
    private String name, company, caddress, nation;
    private String cat_type;
    private String desc;
    private String supertype;
    private String floor;
    private String hierarchy_indexid, hierarchy_id;
    private String purpose;
    private String note;
    private String capacity;
    private String v_id;
    private Boolean cstatus;
    private Boolean cview_status;
    private Boolean eview_status;
    private String rm_email;


    private String total_counts;
    //    private VData vData;
    private String pointer, subject, approver_roleid;
    ArrayList<Invited> invites;
    EmpData employee;
    Room room, entry;
    private String e_time;
    private Boolean projector, screen, internet, telephone, speaker, active, pdfStatus, pslots;
    private CommonObject _id;
    private ArrayList<LocationData> address;
    private long date, start, end;
    private String  o_start, o_end;
    private ArrayList<String> pic;
    private String e_pic;
    private String link;
    private String designation;
    private CommonObject a_time;
    private CommonObject created_time;
    private CommonObject meetingrooms;
    private CategoryData categoryData;

    //    private ArrayList<History> history;
    private CommonObject entrypoints;
    private CommonObject locations;
    private CommonObject rand_id;
    private ArrayList<Agenda> agenda;
    private ArrayList<Boolean> amenities;
    private int samecabin;
    private int verify;
    int status;
    int mverify;
    String mtype, mtype_val;
    //dummy
    private String sort_stamp;

    EmpData host;

    private ArrayList<History> history;


//    getemployeeslots response
    PurposeDetails purposeDetails;
    CategoryDetails categoryDetails;
    UserDetails userDetails;
//


//
//    //notification
//    NotificationObjects meetingData;
//    NotificationObjects employeeData;
    String ntype;
    String mid;


    //microsoft AD
    private String cid;
    private boolean azure;
    private boolean twofa;
    private String clientid;
    private String tenantid;
    private String clientsecret;
    private AutoShutDownModel today;

    private AutoShutDownModel nextday;

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    public String getO_start() {
        return o_start;
    }

    public void setO_start(String o_start) {
        this.o_start = o_start;
    }

    public String getO_end() {
        return o_end;
    }

    public void setO_end(String o_end) {
        this.o_end = o_end;
    }

    public void setHierarchys(CommonObject hierarchys) {
        this.hierarchys = hierarchys;
    }

    public void setA_time(CommonObject a_time) {
        this.a_time = a_time;
    }

    public void setCreated_time(CommonObject created_time) {
        this.created_time = created_time;
    }


    public ArrayList<History> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }

    public AutoShutDownModel getToday() {
        return today;
    }

    public void setToday(AutoShutDownModel today) {
        this.today = today;
    }

    public AutoShutDownModel getNextday() {
        return nextday;
    }

    public void setNextday(AutoShutDownModel nextday) {
        this.nextday = nextday;
    }

    //reccuring
    private boolean recurrence;
    ArrayList<Long> dates;

    //meeting Room approvar
    private ArrayList<String> mr_approvers;
//    private ArrayList<MrApproverStatistics> mr_approver_statistics;
//
//    ArrayList<ReMeetings> re_meetings;

    public EmpData getHost() {
        return host;
    }

    public void setHost(EmpData host) {
        this.host = host;
    }

    public CategoryData getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(CategoryData categoryData) {
        this.categoryData = categoryData;
    }

    public CommonObject getA_time() {
        return a_time;
    }

    public String getMtype() {
        return mtype;
    }

    public String getMtype_val() {
        return mtype_val;
    }

    //    public ArrayList<Pdfs> getPdfs() {
//        return pdfs;
//    }
    public String getRm_email() {
        return rm_email;
    }

    public Boolean getPdfStatus() {
        return pdfStatus;
    }

    public Boolean getPslots() {
        return pslots;
    }

    public ArrayList<Boolean> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<Boolean> amenities) {
        this.amenities = amenities;
    }

    public int getMverify() {
        return mverify;
    }

    public int getVerify() {
        return verify;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public CommonObject getHierarchys() {
        return hierarchys;
    }

    public String getHierarchy_indexid() {
        return hierarchy_indexid;
    }

    public String getHierarchy_id() {
        return hierarchy_id;
    }

    public String getDesignation() {
        return designation;
    }

    public CommonObject getCreated_time() {
        return created_time;
    }

//    public ArrayList<History> getHistory() {
//        return history;
//    }

    public CommonObject getRand_id() {
        return rand_id;
    }

    public ArrayList<Agenda> getAgenda() {
        return agenda;
    }

    public void setAgenda(ArrayList<Agenda> agenda) {
        this.agenda = agenda;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSamecabin() {
        return samecabin;
    }

    public void setSamecabin(int samecabin) {
        this.samecabin = samecabin;
    }

    public String getEntrypoint() {
        return entrypoint;
    }

    public void setEntrypoint(String entrypoint) {
        this.entrypoint = entrypoint;
    }

    public String getMeetingroom() {
        return meetingroom;
    }

    public void setMeetingroom(String meetingroom) {
        this.meetingroom = meetingroom;
    }

    public CommonObject getMeetingrooms() {
        return meetingrooms;
    }


    public CommonObject getEntrypoints() {
        return entrypoints;
    }

    public CommonObject getLocations() {
        return locations;
    }

    public String getE_pic() {
        return e_pic;
    }

    public void setE_pic(String e_pic) {
        this.e_pic = e_pic;
    }

    public ArrayList<String> getPic() {
        return pic;
    }

    public void setPic(ArrayList<String> pic) {
        this.pic = pic;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCat_type() {
        return cat_type;
    }

    public void setCat_type(String cat_type) {
        this.cat_type = cat_type;
    }

    public String getCaddress() {
        return caddress;
    }

    public String getNation() {
        return nation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobilecode() {
        return mobilecode;
    }

    public void setMobilecode(String mobilecode) {
        this.mobilecode = mobilecode;
    }

//    public ArrayList<ApproverStatistics> getApprover_statistics() {
//        return approver_statistics;
//    }
//
//    public void setApprover_statistics(ArrayList<ApproverStatistics> approver_statistics) {
//        this.approver_statistics = approver_statistics;
//    }

    public long getDate() {
        return date;
    }

    public String getApprover_roleid() {
        return approver_roleid;
    }

    public void setApprover_roleid(String approver_roleid) {
        this.approver_roleid = approver_roleid;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public ArrayList<Invited> getInvites() {
        return invites;
    }

    public void setInvites(ArrayList<Invited> invites) {
        this.invites = invites;
    }

    public EmpData getEmployee() {
        return employee;
    }

    public void setEmployee(EmpData employee) {
        this.employee = employee;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getEntry() {
        return entry;
    }

    public void setEntry(Room entry) {
        this.entry = entry;
    }

    public String getE_time() {
        return e_time;
    }

    public void setE_time(String e_time) {
        this.e_time = e_time;
    }

//    public VData getvData() {
//        return vData;
//    }

    public ArrayList<LocationData> getAddress() {
        return address;
    }

    public void setAddress(ArrayList<LocationData> address) {
        this.address = address;
    }

    public CommonObject get_id() {
        return _id;
    }

    public void set_id(CommonObject _id) {
        this._id = _id;
    }

    public RoleDetails getRoleDetails() {
        return roleDetails;
    }

    public void setRoleDetails(RoleDetails roleDetails) {
        this.roleDetails = roleDetails;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNote() {
        return note;
    }

    public String getSupertype() {
        return supertype;
    }

    public void setSupertype(String supertype) {
        this.supertype = supertype;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getV_id() {
        return v_id;
    }

    public Boolean getCstatus() {
        return cstatus;
    }

    public Boolean getcview_status() {
        return cview_status;
    }

    public Boolean geteview_status() {
        return eview_status;
    }

    public String getTotal_counts() {
        return total_counts;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    public Boolean getProjector() {
        return projector;
    }

    public void setProjector(Boolean projector) {
        this.projector = projector;
    }

    public Boolean getScreen() {
        return screen;
    }

    public void setScreen(Boolean screen) {
        this.screen = screen;
    }

    public Boolean getInternet() {
        return internet;
    }

    public void setInternet(Boolean internet) {
        this.internet = internet;
    }

    public Boolean getTelephone() {
        return telephone;
    }

    public void setTelephone(Boolean telephone) {
        this.telephone = telephone;
    }

    public Boolean getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Boolean speaker) {
        this.speaker = speaker;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }


    public EmpData getEmpData() {
        return empData;
    }

    public void setEmpData(EmpData empData) {
        this.empData = empData;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getSort_stamp() {
        return sort_stamp;
    }

    public void setSort_stamp(String sort_stamp) {
        this.sort_stamp = sort_stamp;
    }


    public PurposeDetails getPurposeDetails() {
        return purposeDetails;
    }

    public void setPurposeDetails(PurposeDetails purposeDetails) {
        this.purposeDetails = purposeDetails;
    }

    public CategoryDetails getCategoryDetails() {
        return categoryDetails;
    }

    public void setCategoryDetails(CategoryDetails categoryDetails) {
        this.categoryDetails = categoryDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
//
//
//    public NotificationObjects getMeetingData() {
//        return meetingData;
//    }
//
//    public void setMeetingData(NotificationObjects meetingData) {
//        this.meetingData = meetingData;
//    }
//
//    public NotificationObjects getEmployeeData() {
//        return employeeData;
//    }
//
//    public void setEmployeeData(NotificationObjects employeeData) {
//        this.employeeData = employeeData;
//    }

    public String getNtype() {
        return ntype;
    }

    public void setNtype(String ntype) {
        this.ntype = ntype;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }


    //microsoft AD

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public boolean isAzure() {
        return azure;
    }

    public void setAzure(boolean azure) {
        this.azure = azure;
    }

    public boolean isTwofa() {
        return twofa;
    }

    public void setTwofa(boolean twofa) {
        this.twofa = twofa;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getTenantid() {
        return tenantid;
    }

    public void setTenantid(String tenantid) {
        this.tenantid = tenantid;
    }

    public String getClientsecret() {
        return clientsecret;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public boolean getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(boolean recurrence) {
        this.recurrence = recurrence;
    }

    public ArrayList<Long> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Long> dates) {
        this.dates = dates;
    }

    public ArrayList<String> getMr_approvers() {
        return mr_approvers;
    }

    public void setMr_approvers(ArrayList<String> mr_approvers) {
        this.mr_approvers = mr_approvers;
    }

//    public ArrayList<MrApproverStatistics> getMr_approver_statistics() {
//        return mr_approver_statistics;
//    }
//
//    public void setMr_approver_statistics(ArrayList<MrApproverStatistics> mr_approver_statistics) {
//        this.mr_approver_statistics = mr_approver_statistics;
//    }
//
//    public ArrayList<ReMeetings> getRe_meetings() {
//        return re_meetings;
//    }
//
//    public void setRe_meetings(ArrayList<ReMeetings> re_meetings) {
//        this.re_meetings = re_meetings;
//    }
}
