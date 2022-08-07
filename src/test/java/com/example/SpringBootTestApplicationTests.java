package com.example;

import com.example.entity.data.Account;
import com.example.entity.data.AccountDetail;
import com.example.repo.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@SpringBootTest
class SpringBootTestApplicationTests {

    @Resource
    AccountRepository repository;

    //随便写入什么操作
    @Test
    void contextLoads() {

    }
    //查询操作
    @Test
    void findAccount(){
        //直接根据ID查找
        repository.findById(1).ifPresent(System.out::println);
    }
    //插入操作
    @Test
    void addAccount(){
        Account account = new Account();
        account.setUsername("Admin");
        account.setPassword("123456");
        account = repository.save(account);  //返回的结果会包含自动生成的主键值
        //可以自动获取插入的数值！
        System.out.println("插入时，自动生成的主键ID为："+account.getId());
    }
    //删除2号
    @Test
    void deleteAccount(){
        repository.deleteById(2);   //根据ID删除对应记录
    }

    //查询所有+分页
    @Test
    void pageAccount() {
        //0是第一页的数据，1是每页一个数据
        //repository.findAll(PageRequest.of(0, 1)).forEach(System.out::println);  //直接分页
        repository.findAll(PageRequest.of(0, 2)).forEach(System.out::println);  //直接分页
    }

    //表依赖
    @Test
    void tables(){
        repository.findById(1).ifPresent(System.out::println);
    }

    //需要的时候才会加载被引用表
    @Transactional   //懒加载属性需要在事务环境下获取，因为repository方法调用完后Session会立即关闭
    @Test
    void lazyPrint() {
        repository.findById(1).ifPresent(account -> {
            System.out.println(account.getUsername());   //获取用户名
            System.out.println(account.getDetail());  //获取详细信息（懒加载）
        });
    }

    //联合添加一个user
    @Test
    void addContactAccount(){
        Account account = new Account();
        account.setUsername("伟强");
        account.setPassword("123456");

        AccountDetail detail = new AccountDetail();
        detail.setAddress("甘肃省庆阳市环县");
        detail.setPhone("1234567890");
        detail.setEmail("73281937@qq.com");
        detail.setRealName("贾伟强");

        account.setDetail(detail);

        account = repository.save(account);
        System.out.println("插入时，自动生成的主键ID为："+account.getId()+"，外键ID为："+account.getDetail().getId());
    }

    //一个学生的多个成绩
    @Transactional
    @Test
    void oneToMany() {
        repository.findById(1).ifPresent(account -> {
            account.getScoreList().forEach(System.out::println);
        });
    }

    //一个subject查多个老师
    @Transactional
    @Test
    void manyToOne() {
        repository.findById(3).ifPresent(account -> {
            account.getScoreList().forEach(score -> {
                System.out.println("课程名称："+score.getSubject().getName());
                System.out.println("得分："+score.getScore());
                //System.out.println("任课教师："+score.getSubject().getTeacher().getName());
            });
        });
    }

}
