////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package cn.dotfashion.soa.tc.vo.coordinate.request;
//
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import io.swagger.annotations.ApiParam;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.ToString;
//import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.Range;
//
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import java.util.Date;
//import java.util.Map;
//
//@Data
//@ApiModel(description = "开启全局事务请求")
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//public class GlobalTxBeginReq extends LockReq {
//
//    @ApiModelProperty(notes = "环境标记，中间件开发环境、测试环境复用一套，使用环境标记区分，dev, test, uat, prod")
//    private String envTag;
//
//    @ApiModelProperty(notes = "UUID，避免重复开启全局事务")
//    @NotBlank(message = "客户端标识，不能为空")
//    @Length(min = 1, max = 64, message = "长度不能超过64")
//    private String uuid;
//
//    @ApiModelProperty(notes = "业务标签")
//    private String bizFlag;
//
//    @ApiModelProperty(notes = "所属系统名")
//    private String system;
//
//    @ApiModelProperty(notes = "全局事务对应的业务名")
//    @NotBlank(message = "业务名不能为空")
//    private String business;
//
//    @ApiModelProperty(notes = "全局事务对应的业务名")
//    @NotNull(message = "必须设置超时时间")
//    @Range(min = 1, max = 30 * 60, message = "超时时间必须在30分钟内")
//    private Integer txTimeoutSeconds;
//
//    @ApiModelProperty(notes = "本地事务如需回滚则提供此内容")
//    private RollbackData rollbackData;
//    /**
//     * 本地全局事务表所在库Id
//     */
//    private Integer shardingDbId;
//    /**
//     * 用于异步
//     */
//    @ApiParam(hidden = true)
//    private Date txTimeout;
//
//    @Data
//    @ApiModel(description = "回滚内容")
//    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//    public static class RollbackData {
//
//        private String type = "RPC";
//        private String apiTag;
//
//        private String rollbackPath;
//
//        private String httpMethod;
//
//        private Map<String, String> header;
//
//        private String bodyJson;
//    }
//
//}