package com.sfxcode.sapphire.extension.filter

import com.sfxcode.sapphire.core.value.FXBean
import com.sfxcode.sapphire.extension.control.DataListView
import javafx.beans.property.ReadOnlyObjectProperty
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{ListView, TextField}

class DataListFilter[S <: AnyRef](dataList: DataListView[S])
  extends DataFilter[S](dataList.items, dataList.header) {
  var sortFiltered = true

  var searchField: TextField = addSearchField(dataList.cellProperty.get)
  searchField.setPromptText(dataList.filterPromptProperty.get)

  dataList.filterPromptProperty.onChange((_, oldValue, newValue) => searchField.setPromptText(newValue))

  dataList.cellProperty.onChange((_, oldValue, newValue) => cellPropertyChanged(oldValue, newValue))

  def cellPropertyChanged(oldValue: String, newValue: String): Unit = {
    controlList.clear()
    controlFilterMap.clear()
    controlFilterPropertyMap.clear()
    valueMap.clear()

    filterControlNameMapping.clear()
    filterNameControlMapping.clear()
    searchField.setText("")

    if (dataList.header.value != null)
      dataList.header.value.getChildren.remove(searchField)

    searchField = addSearchField(dataList.cellProperty.get)
    searchField.setPromptText(dataList.filterPromptProperty.get)

  }

  filterResult.onChange {
    dataList.listView.getItems.clear()
    if (filterResult.nonEmpty)
      filterResult.foreach(v => dataList.listView.getItems.add(v))
  }

  filter()

  override def itemsHasChanged(): Unit = {
    super.itemsHasChanged()
    listView.autosize()
    listView.layout()
  }

  def reload(shouldReset: Boolean = false): Unit = {
    listView.setItems(null)
    if (shouldReset)
      reset()
    else
      filter()
  }

  def listView: ListView[FXBean[S]] = dataList.listView

  def selectedBean: FXBean[S] = listView.selectionModel().selectedItemProperty().get()

  def selectedItem: ReadOnlyObjectProperty[FXBean[S]] = listView.selectionModel().selectedItemProperty()

  def selectedItems: ObservableBuffer[FXBean[S]] = listView.selectionModel().selectedItems

}
