package com.bourntec.emailreader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.bourntec.emailreader.models.EmailStoreModel;
import com.bourntec.emailreader.repo.EmailStoreRepository;
import com.bourntec.emailreader.service.EmailStoreService;
import com.bourntec.emailreader.service.impl.EmailStoreServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT /*, properties = "spring.profiles.active=test"*/)
class PersistTests {
	
	private static EmailStoreModel email_one;
	private static EmailStoreModel email_two;
	
	@Mock
    private EmailStoreRepository emailStoreRepository;

    @InjectMocks
    private EmailStoreServiceImpl emailStoreService;

    @BeforeAll
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
    	
        email_one = new EmailStoreModel(
        		"Subject 1", "Test1 <test1@test.com>", "Test 2 <test2@test.com>", 
        		"multipart/mixed; boundary=\"0000000000006692c405b2bb466f\"", 
        		"2020-Oct-28 19:08:03 IST", "Body 1", false, 0, "[]");
        email_two = new EmailStoreModel("Subject 2", "Test 2 <test2@test.com>", "Test1 <test1@test.com>", 
        		"multipart/mixed; boundary=\"0000000000006692c405b2bb466f\"", 
        		"2020-Oct-28 19:08:03 IST", "Body 2", true, 1, "[file.ext]");
    }
    
    
    @Test
    void findAllTest_WhenNoRecord() {

       Mockito.when(emailStoreRepository.findAll()).thenReturn(Arrays.asList());
       assertThat(emailStoreService.findAll().size(), is(0));
       Mockito.verify(emailStoreRepository, Mockito.times(1)).findAll();

    }

    @Test
    void save() {

        Mockito.when(emailStoreRepository.save(email_one)).thenReturn(email_one);
        assertThat(emailStoreService.saveEmail(email_one), is(email_one));
        Mockito.verify(emailStoreRepository, Mockito.times(1)).save(email_one);

        Mockito.when(emailStoreRepository.save(email_two)).thenReturn(email_two);
        assertThat(emailStoreService.saveEmail(email_two).getEmailSubject(), is("Subject 2"));
        Mockito.verify(emailStoreRepository, Mockito.times(1)).save(email_two);
    }
    
    @Test
    void findAllTest_WhenRecord() {

        Mockito.when(emailStoreRepository.findAll()).thenReturn(Arrays.asList(email_one, email_two));
        assertThat(emailStoreService.findAll().size(), is(2));
        assertThat(emailStoreService.findAll().get(0), is(email_one));
        assertThat(emailStoreService.findAll().get(1),is(email_two));
        Mockito.verify(emailStoreRepository, Mockito.times(3)).findAll();

    }

    @Test
    void findById() {

        Mockito.when(emailStoreRepository.findById(1L)).thenReturn(Optional.of(email_one));
        assertThat(emailStoreService.findByEmailId(1L), is(email_one));
        Mockito.verify(emailStoreRepository, Mockito.times(1)).findById(1L);
    }

    

//    @Test
//    void deleteById() {
//        emailStoreService.deleteByEmailId(1L);
//        Mockito.verify(emailStoreRepository, Mockito.times(1)).deleteById(1L);
//    }
    
    
    
}
