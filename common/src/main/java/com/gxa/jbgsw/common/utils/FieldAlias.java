package com.gxa.jbgsw.common.utils;

/**
 * @Author Mr. huang
 * @Date 2023/2/15 0015 16:54
 * @Version 2.0
 */
public class FieldAlias {

    // 网关设备软件版本号
    public final static String SOFTWARE_VER = "softwareVer";
    // 网关设备硬件版本号
    public final static String HARDWARE_VER = "hardwareVer";
    // 角度传感器软件版本号
    public final static String SENSOR_SOFTWARE_VER = "sensorSoftwareVer";
    //角度传感器硬件版本号
    public final static String SENSOR_HARDWARE_VER = "sensorHardwareVer";
    // IMEI
    public final static String IMEI = "imei";
    // IMSI
    public final static String IMSI = "imsi";
    // SIM
    public final static String SIM = "sim";
    // ICCID
    public final static String ICCID = "iccid";
    // 信号强度
    public final static String SEMAPHORE = "semaphore";
    // SINR
    public final static String SINR = "sinr";
    // 角度传感器倾斜角度值
    public final static String LEAN_ANGLE = "leanAngle";
    // 角度传感器开盖角度值(暂时未用)
    public final static String OPEN_ANGLE = "openAngle";
    // 角度传感器倾斜告警阀值(暂时未用)
    public final static String LEAN_ALARM_VALUE = "leanAlarmValue";
    // 角度传感器开盖告警阀值(暂时未用)
    public final static String OPEN_ALARM_VALUE = "openAlarmValue";
    // 甲烷浓度(暂时未用)
    public final static String FIREDAMP = "firedamp";
    // 一氧化碳浓度(暂时未用)
    public final static String CO = "co";
    // 硫化氢浓度(暂时未用)
    public final static String HS = "hs";
    // 污泥高度(暂时未用)
    public final static String MUD_HIGH = "mudHigh";
    // 液位高度(暂时未用)
    public final static String WATER_HIGH = "waterHigh";
    // 当前日志总条目数
    public final static String CURRENT_LOG_NUM = "currentLogNum";
    // 网关当前电池电量
    public final static String CURRENT_BATTERY_NUM = "currentBatteryNum";
    // 角度传感器当前电池电量
    public final static String CURRENT_SENSOR_BATTERY_NUM = "currentSensorBatteryNum";
    // 温度值(暂时未用)
    public final static String TEMPERATURE = "temperature";
    // 气体传感器检测周期
    public final static String GAS_SENSOR_CHECK_CYCLE = "gasSensorCheckCycle";
    // 网关蓝牙MAC地址
    public final static String GATEWAY_MAC = "gatewayMac";
    // 角度传感器蓝牙MAC地址
    public final static String SENSOR_MAC = "sensorMac";
    // 一个唤醒周期内,角度传感器被唤醒次数
    public final static String SENSOR_WAKE_NUM = "sensorWakeNum";
    // 当前网关告警开关状态
    public final static String GATEWAY_ALARM_SWITCH = "gatewayAlarmSwitch";
    // 当前角度传感器告警开关状态
    public final static String SENSOR_ALARM_SWITCH = "sensorAlarmSwitch";
    // 当前工作模式
    public final static String MODULE_TYPE = "moduleType";
    // 网关休眠时蓝牙广播周期
    public final static String BLUETOOTH_SLEEP_CYC = "bluetoothSleepCyc";
    // 角度告警去抖次数
    public final static String SENSOR_ALARM_SHAKE_NUM = "sensorAlarmShakeNum";
    // 井盖在位监控开关
    public final static String COVER_IS_SWITCH = "coverIsSwitch";
    // 角度传感器与网关绑定时信号值(判断井盖在位的标准蓝牙信号值)
    public final static String NET_SIGNAL_VALUE = "netSignalValue";
    // 角度传感器与网关蓝牙信号值
    public final static String BLUETOOTH_SIGNAL_VALUE = "bluetoothSignalValue";
    // 判断井盖在位蓝牙信号差值（判断井盖在位蓝牙信号差值）
    public final static String COVER_SIGNAL_VALUE = "coverSignalValue";
    // 网关与角度传感器最近一次通信时间（判断井盖在位蓝牙信号差值）
    public final static String LAST_COMMUNICATION_TIME = "lastCommunicationTime";

    // 漏电状态(1 字节),HEX 格式,0-不漏电;1-漏电
    public final static String IS_LEAKAGE = "isLeakage";
    // 当前井盖在位状态(1 字节),HEX 格式,0-在位;1-不在位
    public final static String IS_STATIC = "isStatic";
    // 水满传感器：当前水满状态(1 字节),HEX 格式,0-没有水满(false);1-水满(true)， 转换： true 水满， false 没有水满
    public final static String IS_FULL = "isFull";
    // 黄色水位传感器：当前黄色水位告警状态(1 字节),HEX 格式,0-没有告警(false);1-告警(true)
    public final static String CURRENT_YELLOW_ALARM_STATUS = "currentYellowAlarmStatus";
    // 橙色水位传感器：当前黄橙色水位告警状态(1 字节),HEX 格式,0-没有告警(false);1-告警(true)
    public final static String CURRENT_ORANGE_ALARM_STATUS = "currentOrangeAlarmStatus";
    // 红色水位传感器：当前红色水位告警状态(1 字节),HEX 格式,0-没有告警(false);1-告警(true)
    public final static String CURRENT_RED_ALARM_STATUS = "currentRedAlarmStatus";


    // IP
    public final static String IP = "ip";
    // port
    public final static String PORT = "port";
    // domain
    public final static String DOMAIN = "domain";
    // 自动唤醒周期(数据库存储为分钟, 给底软的转换成秒)
    public final static String TERMINAL_HEARTBEAT_DURATION = "terminalHeartbeatDuration";
    // 角度传感器自动唤醒周期(数据库存储为分钟, 给底软的转换成秒)
    public final static String SENSOR_HEARTBEAT_DURATION = "sensorHeartbeatDuration";
    // 气体传感器检测周期
    public final static String GAS_HEARTBEAT_DURATION = "gasSensorCheckCycle";
    // 上报日志条数
    public final static String LOG_NUM = "logNum";
    // 甲烷告警阈值
    public final static String CH4_LEVEL3 = "ch4Level3";
    // 甲烷告警阈值
    public final static String CH4_LEVEL2 = "ch4Level2";
    // 甲烷告警阈值
    public final static String CH4_LEVEL1 = "ch4Level1";
    // 角度传感器震动阀值(HEX格式（默认250 mg）
    public final static String ANGLE_QUAKE = "angleQuake";
    // 角度传感器静止阀值 (HEX格式（默认150 mg） )
    public final static String ANGLE_STATIC_MAX_VALUE = "angleStaticMaxValue";
    // 角度传感器判断静止时间（默认2秒） )
    public final static String ANGLE_STATIC_TIME = "angleStaticTime";
    // 消警角度阀值(HEX格式（默认10度） )
    public final static String ANGLE_KILL_ALARM_VALUE = "angleKillAlarmValue";
    // 网关告警开关
    public final static String ALARM_IS_SWITCH = "alarmIsSwitch";
    // 角度传感器告警开关
    public final static String SENSOR_SWITCH = "sensorSwitch";
    // 网关蓝牙广播周期
    public final static String BROADCAST_CYC = "broadcastCyc";
    // 判断井盖在位蓝牙信号差值
    public final static String ANGLE_BLUETOOTH_SIGNAL_VALUE = "angleBluetoothSignalValue";
    // 监控模式： 0 监测模式 1 测试模式
    public final static String MONITOR_MODEL = "monitorModel";
    // 水位/水满去抖时间，默认为5秒
    public final static String WATER_REMOVE_QUAKE_TIME = "waterRemoveQuakeTime";
    // 角度传感器一个唤醒周期内,产生开盖告警上限次数，默认6次
    public final static String QX_ALARM_NUM = "qxAlarmNum";
    // 倾斜当天异常唤醒次数，默认：200
    public final static String QX_DAY_ABNORMAL_WAKE_NUM = "qxDayAbnormalWakeNum";
    // 设备超时时间
    public final static String TIMEOUT = "timeout";
    // 网关电池电量恢复默认
    public final static String GATEWAY_BATTER_DEFAULT = "gatewayBatterDefault";
    // 气体红色告警阈值(单位:mm)
    public final static String GAS_THICKNESS_RED = "gasThicknessRed";
    // 气体橙色告警阈值(单位:mm)
    public final static String GAS_THICKNESS_ORANGE = "gasThicknessOrange";
    // 气体黄色告警阈值(单位:mm)
    public final static String GAS_THICKNESS_YELLOW = "gasThicknessYellow";
    // 气体蓝色告警阈值(单位:mm)
    public final static String GAS_THICKNESS_BLUE = "gasThicknessBlue";

    // 红色水位阈值（单位厘米）
    public final static String  WATER_LEVEL_RED = "waterLevelRed";
    // 蓝色水位阈值（单位厘米）
    public final static String  WATER_LEVEL_BLUE = "waterLevelBlue";
    // 黄色水位阈值（单位厘米）
    public final static String  WATER_LEVEL_YELLOW = "waterLevelYellow";
    // 橙色水位阈值（单位厘米）
    public final static String  WATER_LEVEL_ORANGE = "waterLevelOrange";


    // 积水状态下,水深检测周期(单位:秒)
    public final static String  WATER_DEPTH_CHECK_DURATION = "waterDepthCheckDuration";
    // 当前积水深度
    public final static String CURRENT_WATER_DEPTH = "currentWaterDepth";

    // 当前气体浓度
    public final static String CURRENT_GAS_THICKNESS = "currentGasThickness";
    // 当前漏电状态
    public final static String CURRENT_STATUS = "current_status";


    // 当前 Lora 网关绑定的节点个数  HEX 格式 节点最多 100 个
    public final static String  NODE_NUM = "nodeNum";
    // Lora 模组状态
    public final static String  LORA_MODULE_STATUS = "loraModuleStatus";
    // 4G 模组状态
    public final static String  FOUR_G_MODULE_STATUS = "fourGModuleStatus";
    // Lora 网关电池电压
    public final static String  LORA_GATEWAY_VOLTAGE = "loraGatewayVoltage";


    // 锁舌状态
    public final static String  LOCK_BOLT_STATUS = "lockBoltStatus";
    // 外盖状态
    public final static String  COVER_STATUS = "coverStatus";
    // 内盖状态
    public final static String  INNER_STATUS = "innerStatus";
    // 湿度
    public final static String  HUMIDITY = "humidity";
    // 开锁时间, unix时间戳
    public final static String  UNLOCKING_AT = "unlockingAt";
    // 开锁类型
    public final static String  OPEN_LOCK_TYPE = "openLockType";
    // 锁当前工作模式
    public final static String  MODULE_TYPE_1 = "moduleType1";
    // 锁当前工作模式
    public final static String  MODULE_TYPE_2 = "moduleType2";
    // 锁当前工作模式
    public final static String  MODULE_TYPE_3 = "moduleType3";
    // 锁当前工作模式
    public final static String  MODULE_TYPE_4 = "moduleType4";
    // 锁当前工作模式
    public final static String  MODULE_TYPE_5 = "moduleType5";
    // 锁当前工作模式
    public final static String  MODULE_TYPE_6 = "moduleType6";
    // 锁当前工作模式
    public final static String  MODULE_TYPE_7 = "moduleType7";
    // 锁当前工作模式
    public final static String  MODULE_TYPE_8 = "moduleType8";

    // 时间戳
    public final static String  CREATE_AT = "createAt";
    // 序列号
    public final static String  SERIAL_NO = "serialNo";
    // 序列号
    public final static String  ALARM_VALUE = "alarmValue";
    // 指令
    public final static String DIRECTIVE = "directive";
    // 日志类型
    public final static String INS = "ins";
    // 日志体
    public final static String BODY = "body";

    // 序号
    public final static String NO = "no";
    // 设备标识
    public final static String FLAG = "flag";
    // 软件版本大小
    public final static String SIZE = "size";
    // 升级数据部分
    public final static String CONTENT = "content";
    // 节点IMEI
    public final static String NODE = "node";
    // 当前 Lora 网关地址
    public final static String LORA_GATEWAY_NODE_ADDR = "loraGatewayNodeAddr";



    // 溶解氧
    public final static String DISSOLVEDOXYGEN = "dissolvedOxygen";
    // ph值
    public final static String PHVALUE = "phValue";
    // 浊度
    public final static String TURBIDITY = "turbidity";
    // 电导率
    public final static String CONDUCTIVITY = "conductivity";
    // 蓝绿藻
    public final static String BLUEGREENALGAE = "blueGreenAlgae";
    // 叶绿素
    public final static String CHLOROPHYL = "chlorophyl";

    // 水压力传感器
    public final static String WATER_PRESSURE_SENSOR = "WaterPressureSensor";



    // 设备状态
    public final static String STATUS = "status";
    // 升级状态
    public final static String UPGRADE_STATUS = "upgradeStatus";
    // 角度传感器参数配置结果
    public final static String CONFIG_STATUS = "configStatus";

    // 设备类型
    public final static String DEVICE_TYPE = "deviceType";


    // 升级数据部分
    public final static String ERR_CONTENT = "-1";
    // 索引
    public static final String INDEX = "index";
}