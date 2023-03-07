package com.study.mongodb.vo;

import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "UserLog")
@ToString
public class TldwUserLog  {

    @Id
    private String id;
    private String actor;
    private String actorName;
    private String ip;
    private Date logTime;
    private LogType logType;
    private LogAction logAction;
    private String feature;
    private String module;
    private String summary;
    private String sourceId;
    private String targetId;
    // 加一个chartUUID
    private String chartUUID;

    public TldwUserLog() {
        super();
    }

    // user.getAccount(), user.getName(), ip, LogType.Analysis,
    // LogAction.Query, Feature.AnalysisFeature.ExtendedAnalysis

    public TldwUserLog(String actor, String actorName, String ip, LogType logType, LogAction logAction,
        String feature) {
        super();
        this.actor = actor;
        this.actorName = actorName;
        this.ip = ip;
        this.logType = logType;
        this.logAction = logAction;
        this.feature = feature;
    }

    public TldwUserLog(String actor, String actorName, String ip, LogType logType, LogAction logAction, String feature,
        String targetId) {
        this(actor, actorName, ip, logType, logAction, feature);
        this.targetId = targetId;
    }

    public TldwUserLog(String actor, String actorName, String ip, LogType logType, LogAction logAction, String feature,
        String sourceId, String targetId) {
        this(actor, actorName, ip, logType, logAction, feature, targetId);
        this.sourceId = sourceId;
    }

    public TldwUserLog(String actor, String actorName, String ip, Date logTime, LogType logType, LogAction logAction,
        String feature, String module, String summary, String sourceId, String targetId) {
        super();
        this.actor = actor;
        this.actorName = actorName;
        this.ip = ip;
        this.logTime = logTime;
        this.logType = logType;
        this.logAction = logAction;
        this.feature = feature;
        this.module = module;
        this.summary = summary;
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public LogAction getLogAction() {
        return logAction;
    }

    public void setLogAction(LogAction logAction) {
        this.logAction = logAction;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getChartUUID() {
        return chartUUID;
    }

    public void setChartUUID(String chartUUID) {
        this.chartUUID = chartUUID;
    }

    @Override
    public TldwUserLog clone() {
        if (StringUtils.isNotBlank(id)) {
            return new TldwUserLog(this.getActor(), this.getActorName(), this.getIp(), this.getLogTime(),
                this.getLogType(), this.getLogAction(), this.getFeature(), this.getModule(), this.getSummary(),
                this.getSourceId(), this.getTargetId());
        }
        return this;
    }

}
