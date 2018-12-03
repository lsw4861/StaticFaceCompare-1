package com.hzgc.manage.service.impl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.hzgc.manage.dao.*;
import com.hzgc.manage.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Client client;

    @Test
    public void insert() throws IOException {
        int n=0;
        List<Person> list = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("C:\\Users\\ZBL\\Desktop\\compare_index.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        while ((line = br.readLine()) != null) {
            Person person = new Person();
            person.setId(UUID.randomUUID().toString().replace("-", ""));
            person.setXm(com.hzgc.manage.service.impl.RandomValue.getChineseName());
            person.setXb(RandomValue.getSex());
            String sfz=RandomValue.getSfz();
            person.setSfz(RandomValue.getSfz());
            person.setMz("汉族");
            person.setSr(RandomValue.getSr());
            person.setSsssqx(RandomValue.getssssqx());
            person.setMlxz(RandomValue.getMLXZ());
            person.setJd(RandomValue.getStreet());
            person.setCsd(RandomValue.getssssqx());
            person.setCym(RandomValue.getChineseName());
            person.setMp(RandomValue.getStreet());
            person.setJg(RandomValue.getssssqx());
            String[] strings = line.split(":");
            System.out.println(strings.length);
            for (int k = 0; k < strings.length; k++) {
                System.out.println("string" + k + "::" + strings[k]);
            }
            if (strings.length == 4) {
                n++;
                person.setTp(strings[0]);
                //        person.setTpbase("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");
                //        person.setBittzz("Fb6JV0ucI87NskXQ7k6FLHnSTmhQXTjwBMWqktsk/bc=");
                person.setBittzz(strings[2]);
                //        person.setTzz("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");
                person.setTzz(strings[3]);
                FileWriter fw = new FileWriter("C:\\Users\\ZBL\\Desktop\\index1201\\"+sfz.substring(0,4)+".txt", true);
                fw.write(person.getId() + "_" + strings[2] + "\n");
                fw.close();
                list.add(person);
            }
        }
        long l = System.currentTimeMillis();
        System.err.println(l);
        personRepository.saveAll(list);
        long ll = System.currentTimeMillis();
        System.err.println(ll);
        System.out.println("num"+n);
        System.err.println(ll - l);
    }
    @Test
    public void save() {
//        userRepository.deleteById("6cad4c8f-ea58-4848-b5d1-4165c3b2c39b");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Person> all = personRepository.findAll(pageable);
        Page<Person> list = personRepository.findByXmLike("*红*", pageable);
        Page<Person> bySfzLike = personRepository.findBySfzLike("*194112*", pageable);
        System.out.println(list);
    }

    @Test
    public void chongfu(){

//        08814b738cea4e78bf9f46ed429db6d2

       Person byId = personRepository.findById("08814b738cea4e78bf9f46ed429db6d2").get();


       for(int i=0; i< 100; i++){
           Person person = new Person();
           BeanUtil.copyProperties(byId, person);
           person.setId(IdUtil.simpleUUID());
           person.setXm(RandomValue.getChineseName());
           personRepository.save(person);
           client.addData(person.getId(),person.getBittzz(),person.getSfz());

       }

    }
}