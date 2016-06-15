mahout-solr
============

###概述：
+ 针对1500W的user-item-preference:
 + 启动时将user-item-perference加载到内存中
 + 推荐直接读取已经计算好的数据，总体100ms
 + 更新数据时，更新内存中的perference和dataModel

+ 测试计时：2200W的测试数据 

####解决大数据量方案：



