# 对GCLogAnalysis进行GC算法和JVM内存调整验证
## 第一组观察512M内存不同GC算法表现
结论：
1. 暂停时间Stw优劣顺序：G1<CMS<Serial GC<Parallel GC，其中串行GC和并行GC相差8ms；
2. YGC次数：Parallel GC<Serial GC<CMS<G1，YGCT：Parallel GC<Serial GC<G1<CMS；
3. FGC次数：G1<CMS<Serial GC<Parallel GC，FGCT：G1<CMS<Serial GC<Parallel GC
暂停时间敏感的系统考虑使用G1 GC算法，同时要观察业务压测时系统CPU的消耗。
   
## 第二组观察4g内存不同GC算法表现
结论：
1. 暂停时间Stw优劣顺序，由于内存足够大，G1 GC和CMS GC都为0：Parallel GC<Serial GC
2. YGC次数：Serial GC=Parallel GC<CMS<G1，YGCT：Parallel GC<Serial GC<G1<CMS；
3. FGC次数和FGCT均为0。
当内存足够大时，CMS GC和G1 GC在Stw指标上同样优秀，同第一组比Stw均有减小。

## 第三组观察G1 GC不同内存大小算法表现
结论：
1. 内存增大，Stw缩短为0，YGC次数下降，总耗时有所增加，平均耗时也增加。

## GC算法总结链接：https://www.evernote.com/l/AMannpmgmDBDNJMNttFQRh_DsqMMIcx4K5o

## 验证结果链接：https://www.evernote.com/l/AMbvQYvc_H5EM7UvkFZcphlfeu9-BVCLVD8