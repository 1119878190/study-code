# 通过 sharding-jdbc 进行分库分表


## 分库分表后需要考虑的问题

 1、分页查询的性能优化问题。由于本身limit查询就是一个性能极差的查询,limit越大，性能越差。</p>
 2、由于分库分表，要确保归并结果获取到的数据是准确的。