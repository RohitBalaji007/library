package com.example.Library.Repository;

import com.example.Library.Model.BorrowModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface BorrowRepository extends JpaRepository<BorrowModel,Integer> {

    @Query("select b from BorrowModel b where b.customerId = :customerId")
    List<BorrowModel> borrowGet(int customerId);
    @Query("select bk.Title from BookModel bk, BorrowModel br where bk.ISBN=br.ISBN group by br.ISBN having count(br.ISBN)>=4")
    List<String> popularBook();

    @Modifying
    @Query("update BorrowModel b set b.returnDate = CURRENT_DATE where b.customerId = :customerId and b.ISBN = :ISBN")
    void returnDate(@Param("customerId") int customerId,@Param("ISBN") int ISBN );

    @Query("select b from BorrowModel b where (timestampdiff(DAY, b.borrowDate, b.returnDate) >= 10) or (timestampdiff(DAY, b.borrowDate, current_date) >= 10)")
    List<BorrowModel> overDue();
    @Query("SELECT count(*)  FROM BorrowModel  where customerId = :customerId group by customerId")
    int countCusBook(int customerId);
    @Query("SELECT count(*) FROM BorrowModel  where ISBN = :ISBN group by ISBN")
    int countBook(@Param("ISBN") int ISBN);


}