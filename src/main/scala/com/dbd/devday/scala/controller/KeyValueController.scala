package com.dbd.devday.scala.controller

import java.util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import com.dbd.devday.scala.db.KeyValueStoreDAO
import com.dbd.devday.scala.model.KeyValue

@Controller
@RequestMapping(Array("/devday/kv"))
class KeyValueController @Autowired() (keyValueStoreDAO: KeyValueStoreDAO) {

  @RequestMapping(value = Array("{userId}"), method = Array(RequestMethod.GET))
  @ResponseBody def list(@PathVariable userId: String): util.List[KeyValue] = {
    keyValueStoreDAO.findByUser(userId)
  }

  @RequestMapping(value = Array("{userId}"), method = Array(RequestMethod.POST))
  @ResponseStatus(value = HttpStatus.ACCEPTED) def save(@PathVariable userId: String, @RequestBody keyValue: KeyValue) {
    keyValueStoreDAO.save(userId, keyValue)
  }
}

