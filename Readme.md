###命名规则
- handler 为消息处理器，用于接受消息
- controller 为游戏对象的处理器
- manager 管理器，用于管理各个模块，应该为全局唯一,可使用单例模式由spring进行管理，
如排行榜管理器、场景管理器

###设计原则
- 将动态数据（需要持久化，或者内存中使用的）与静态数据（通过配置获得的）分开。
- 先以一种简单可行的方式将游戏快速的设计出来，后期需要根据高并发扩展时再进行扩展

###消息类型约定
- 约定请求消息command使用单数，返回消息使用双数
- 1000 登录模块使用
- 1002 提示模块使用
- 1003 场景模块使用
- 1100 房间模块使用
- 1200 战斗模块使用


###进度安排
- 2017-8-10 ~ 2017-8-15杨明伟将服务器功能初步完成，可进行匹配战斗