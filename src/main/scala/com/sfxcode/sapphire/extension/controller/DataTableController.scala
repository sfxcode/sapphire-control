package com.sfxcode.sapphire.extension.controller


import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.layout.HBox

import com.sfxcode.sapphire.core.controller.ViewController
import com.sfxcode.sapphire.core.value.FXBean
import com.sfxcode.sapphire.extension.filter.DataTableFilter
import com.typesafe.scalalogging.LazyLogging

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import com.sfxcode.sapphire.core.Includes._


abstract class DataTableController extends ViewController with LazyLogging {

  implicit def listToProperty(list:Seq[R]):ObjectProperty[ObservableBuffer[FXBean[R]]] = {
    ObjectProperty(this,"dataTableItems", list)
  }

  type R <: AnyRef

  def ct: ClassTag[R]

  // reflection
  val mirror = ru.runtimeMirror(ct.runtimeClass.getClassLoader)
  val members = mirror.classSymbol(ct.runtimeClass).asType.typeSignature.members.toList.reverse
  logger.debug(members.collect({ case x if x.isTerm => x.asTerm }).filter(t => t.isVal || t.isVar).map(m => m.name.toString).toString())

  @FXML
  var table: TableView[FXBean[R]] = _

  @FXML
  var searchBox: HBox = _

  var tableFilter: DataTableFilter[R] = _

  def items: ObjectProperty[ObservableBuffer[FXBean[R]]]

  override def didGainVisibilityFirstTime() {
    super.didGainVisibilityFirstTime()
    tableFilter = new  DataTableFilter[R](table, items, ObjectProperty(this,"",searchBox))(ct)

    if (shouldAddColunns)
      tableFilter.addColumns()

    initTable(tableFilter)

    tableFilter.selectedItem.onChange((_, oldValue, newValue) => selectedTableViewItemDidChange(oldValue, newValue))
    tableFilter.selectedItems.onChange((source, changes) => {
      selectedItemsDidChange(source, changes)
    })
  }

  def shouldAddColunns = true



  def initTable(tableFilter: DataTableFilter[R]): Unit = {

  }

  def selectedItemsDidChange(source:ObservableBuffer[FXBean[R]], changes:Seq[ObservableBuffer.Change]): Unit = {
    logger.debug("new values count: %s".format(source.size))
  }

  def selectedTableViewItemDidChange(oldValue: FXBean[R], newValue: FXBean[R]): Unit = {
    logger.debug("new value: %s".format({
      if (newValue != null)
        newValue.bean
      else
        null
    }))
  }


}

