package org.magenta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.magenta.annotations.InjectSequence;
import org.magenta.testing.domain.company.Address;
import org.magenta.testing.domain.company.AddressGenerator;
import org.magenta.testing.domain.company.Contract;
import org.magenta.testing.domain.company.ContractGenerator;
import org.magenta.testing.domain.company.Employee;
import org.magenta.testing.domain.company.Employee.Id;
import org.magenta.testing.domain.company.EmployeeGenerator;
import org.magenta.testing.domain.company.EmployeeGenerator2;
import org.magenta.testing.domain.company.EmployeeGenerator3;
import org.magenta.testing.domain.company.EmployeeGenerator4;
import org.magenta.testing.domain.company.Occupation;
import org.magenta.testing.domain.company.PhoneNumber;
import org.magenta.testing.domain.company.PhoneNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Supplier;
import com.google.common.reflect.TypeToken;

public class FixtureFactoryGeneratedByTest {

  private static final Logger log = LoggerFactory.getLogger(FixtureFactoryGeneratedByTest.class);

  @Test
  public void testMissingSequenceForGenerator() {

    // setup fixtures

    NewFixtureFactory fixtures = createRootNewFixtureFactory();
    // exercise sut

    try {
      fixtures.newDataSet(Employee.class).generatedBy(new EmployeeGenerator());
      DataSet<Employee> actual = fixtures.dataset(Employee.class);
      actual.any();
      fail("expecting"+DataGenerationException.class.getName());
    } catch (DataGenerationException dge) {
      assertThat(dge).hasMessageContaining("Employee").hasRootCauseInstanceOf(DataSetNotFoundException.class);
    }

  }


  @Test
  public void testAGeneratorUsingAPredeterminedInjectedSequence(){

    //setup fixtures
    Occupation[] expectedOccupation = new Occupation[]{Occupation.ENGINEER, Occupation.MANAGEMENT, Occupation.TECHNICIAN, Occupation.TESTER};

    NewFixtureFactory fixtures = createRootNewFixtureFactory();
    fixtures.newDataSet(Occupation.class).composedOf(expectedOccupation);
    //exercise sut


    fixtures.newDataSet(Employee.class).generatedBy(new EmployeeGenerator());
    DataSet<Employee> actual = fixtures.dataset(Employee.class);

    //verify outcome
    assertThat(actual.getType()).isEqualTo(TypeToken.of(Employee.class));
    assertThat(actual.list(4)).extracting("occupation").containsExactly(expectedOccupation);
  }

  @Test
  public void testAGeneratorUsingDifferentInjectedSequence(){

    //setup fixtures
    Occupation[] expectedOccupation = new Occupation[]{Occupation.ENGINEER, Occupation.MANAGEMENT, Occupation.TECHNICIAN, Occupation.TESTER};

    NewFixtureFactory fixtures = createRootNewFixtureFactory();
    fixtures.newDataSet(Occupation.class).composedOf(expectedOccupation);
    fixtures.newGenerator(Address.class).generatedBy(new AddressGenerator());
    fixtures.newDataSet(Employee.class).generatedBy(new EmployeeGenerator2());
    DataSet<Employee> actual = fixtures.dataset(Employee.class);

    //exercise sut and verify outcome

    assertThat(actual.getType()).isEqualTo(TypeToken.of(Employee.class));
    assertThat(actual.list(4)).extracting("occupation").containsExactly(expectedOccupation);
    assertThat(actual.list(4)).extracting("address").doesNotContainNull();
  }

  @Test
  public void testAGeneratorUsingAnInjectedDataSet(){

    //setup fixtures
    NewFixtureFactory fixtures = createRootNewFixtureFactory();
    fixtures.newDataSet(Occupation.class).composedOf(Occupation.values());
    fixtures.newDataSet(Address.class).generatedBy(new AddressGenerator());
    fixtures.newGenerator(PhoneNumber.class).generatedBy(new PhoneNumberGenerator());
    fixtures.newDataSet(Employee.class).generatedBy(new EmployeeGenerator3());
    DataSet<Employee> actual = fixtures.dataset(Employee.class);

    //exercise sut and verify outcome

    assertThat(actual.getType()).isEqualTo(TypeToken.of(Employee.class));
    assertThat(actual.any().getPhoneNumbers()).isNotEmpty();

  }

  @Test
  public void testAGeneratorUsingAnUniqueSequence(){
    //setup fixtures
    NewFixtureFactory fixtures = createRootNewFixtureFactory();
    fixtures.newDataSet(Employee.Id.class).transformed(id->Employee.Id.value((Long)id)).composedOf(1L,2L,3L);
    fixtures.newDataSet(Occupation.class).composedOf(Occupation.values());
    fixtures.newDataSet(Address.class).generatedBy(new AddressGenerator());
    fixtures.newGenerator(PhoneNumber.class).generatedBy(new PhoneNumberGenerator());
    fixtures.newDataSet(Employee.class).generatedBy(new EmployeeGenerator4());
    DataSet<Employee> actual = fixtures.dataset(Employee.class);

    //exercise sut and verify outcome
    //because only three ids
    assertThat(actual.getSize()).isEqualTo(3);

    actual.list().forEach(e->print(e));
  }

  @Test
  public void testAGeneratorUsingAnUniqueSequenceThatIsGenerated(){
    //setup fixtures
    final AtomicLong idCount = new AtomicLong(0);

    NewFixtureFactory fixtures = createRootNewFixtureFactory();
    fixtures.newDataSet(Employee.Id.class).transformed(id->Employee.Id.value((Long)id)).generatedBy(()->idCount.incrementAndGet());
    fixtures.newDataSet(Occupation.class).composedOf(Occupation.values());
    fixtures.newDataSet(Address.class).generatedBy(new AddressGenerator());
    fixtures.newGenerator(PhoneNumber.class).generatedBy(new PhoneNumberGenerator());
    fixtures.newDataSet(Employee.class).generatedBy(new EmployeeGenerator4());
    DataSet<Employee> actual = fixtures.dataset(Employee.class);

    //exercise sut and verify outcome
    assertThat(actual.getSize()).isEqualTo(4);

    actual.list().forEach(e->print(e));
  }

  @Test
  public void testAGeneratorUsingAnUniqueSequenceThatIsGeneratedFromStaticData(){
    //setup fixtures

    NewFixtureFactory fixtures = createRootNewFixtureFactory();
    fixtures.newDataSetOf(1L, 2L);

    //The id generator is injected with a dataset of longs just above
    //fixtures.newDataSet(Employee.Id.class).transformed(id->Employee.Id.value((Long)id)).generatedBy(new IdGenerator());
    fixtures.newDataSet(Employee.Id.class).transformed(id->Employee.Id.value((Long)id)).generatedBy(new IdGenerator());
    fixtures.newDataSet(Occupation.class).composedOf(Occupation.values());

    fixtures.newDataSet(Address.class).generatedBy(new AddressGenerator());
    fixtures.newGenerator(PhoneNumber.class).generatedBy(new PhoneNumberGenerator());
    fixtures.newGenerator(Employee.class).generatedBy(new EmployeeGenerator4());


    DataSet<Employee.Id> ids = fixtures.dataset(Employee.Id.class);
    DataSet<Employee> actual = fixtures.dataset(Employee.class);

    //exercise sut and verify outcome

    //because Employee.Id is unique in the EmployeeGenerator4, then the number of generated employees must be the same
    //size as the Employee.id size
    assertThat(ids).as("the employee ids").containsOnly(Id.value(1L),Id.value(2L)).hasSize(2);
    assertThat(actual.getSize()).as("the number of generated employees").isEqualTo(ids.getSize());
    assertThat(actual.getSize()).as("the number of generated employees").isEqualTo(ids.getSize());

    assertThat(actual).extracting("employeeId", Employee.Id.class).containsOnly(Id.value(1L),Id.value(2L)).hasSize(2);


    actual.list().forEach(e->print(e));
  }




  @Test
  public void testAGeneratorWithASpecifiedDefaultSize(){

    //setup fixtures
    NewFixtureFactory fixtures = createRootNewFixtureFactory();

    //exercise sut
    fixtures.newDataSet(PhoneNumber.class).generatedBy(new PhoneNumberGenerator(), 20);
    DataSet<PhoneNumber> actual =fixtures.dataset(PhoneNumber.class);

    //verify outcome
    assertThat(actual.list()).hasSize(20);
  }

  @Test
  public void testAGeneratorWithASpecificSize(){

    //setup fixtures
    NewFixtureFactory fixtures = createRootNewFixtureFactory();

    //exercise sut
    fixtures.newDataSet(PhoneNumber.class).generatedBy(new PhoneNumberGenerator(), 20);
    DataSet<PhoneNumber> actual =fixtures.dataset(PhoneNumber.class);

    //verify outcome
    assertThat(actual.list(30)).hasSize(30);
  }

  @Test
  public void testAGeneratorOfIterable(){

    //setup fixtures
    NewFixtureFactory fixtures = createRootNewFixtureFactory();

    fixtures.newDataSetOf(Occupation.values());
    fixtures.newLazyDataSet(Address.class, new AddressGenerator());
    fixtures.newGenerator(PhoneNumber.class).generatedBy(new PhoneNumberGenerator());
    fixtures.newDataSet(Employee.class).generatedBy(new EmployeeGenerator3());
    fixtures.newDataSet(Contract.class).generatedAsIterableBy(new ContractGenerator());

    //exercise sut

    DataSet<Contract> actual =fixtures.dataset(Contract.class);
    DataSet<Employee> employees =fixtures.dataset(Employee.class);

    //verify outcome
    assertThat(actual.list()).extracting("employee").containsOnly(employees.array());
  }

  @Test
  public void testAFilteredGenerator(){

    //setup fixtures
    NewFixtureFactory fixtures = createRootNewFixtureFactory();
    fixtures.newGenerator(PhoneNumber.class).generatedBy(new PhoneNumberGenerator(),3);


    //exercise sut

    DataSet<PhoneNumber> phones =fixtures.dataset(PhoneNumber.class);

    List<PhoneNumber> filteredPhones = phones.filter(phone -> phone.getPhoneNumber().startsWith("123")).list();

    //verify outcome
    assertThat(filteredPhones).hasSize(3);
  }

  @Test
  public void testATransformedGenerator(){

    //setup fixtures
    NewFixtureFactory fixtures = createRootNewFixtureFactory();

    PhoneNumber expected = new PhoneNumber();
    expected.setPhoneNumber("123-4567");

    fixtures.newDataSet(String.class).transformed((PhoneNumber p)->p.getPhoneNumber()).generatedBy(()->expected,3);


    //exercise sut

    DataSet<String> phones =fixtures.dataset(String.class);

    List<String> filteredPhones = phones.list();

    //verify outcome
    assertThat(filteredPhones).containsOnly("123-4567");
  }


  private NewFixtureFactory createRootNewFixtureFactory() {
    return Magenta.newFixture();
  }

  private void print(Object e) {
    log.debug(e.toString());
  }

  public static class IdGenerator implements Supplier<Long>{

    @InjectSequence
    private Sequence<Long> numbers;

    @Override
    public Long get() {
      return numbers.next();
    }

  }
}
