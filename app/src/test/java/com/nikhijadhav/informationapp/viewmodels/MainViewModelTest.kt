package com.nikhijadhav.informationapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nikhijadhav.informationapp.models.Row
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    lateinit var mainViewModel: MainViewModel
    var mockTitle: MutableLiveData<String> =  MutableLiveData<String>()
    var mockInformationList: MutableLiveData<ArrayList<Row>> = MutableLiveData<ArrayList<Row>>()

    lateinit var observerTitle: Observer<MutableLiveData<String>>
    lateinit var observerInformationList: Observer<MutableLiveData<ArrayList<Row>>>
    lateinit var rowInformationList :ArrayList<Row>
    var expectedTitle = "Canada"
    var expectedSize = 100

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mainViewModel = mock(MainViewModel::class.java)
        observerTitle = mock(Observer<MutableLiveData<String>> { t -> print("Title "+t!!.value) }.javaClass)
        observerInformationList = mock(Observer<MutableLiveData<ArrayList<Row>>> { t -> print("InformationList "+t!!.value!!.size) }.javaClass)

        mockTitle.value = expectedTitle
        mockTitle.observeForever { observerTitle }
        Mockito.`when`(mainViewModel.title).thenReturn(mockTitle)

        rowInformationList = Mockito.spy<ArrayList<Row>>(ArrayList())
        mockInformationList.value = rowInformationList
        //Mockito.doReturn(100).`when`(rowInformationList.size)
        mockInformationList.observeForever { observerInformationList }
        Mockito.`when`(mainViewModel.informationList).thenReturn(mockInformationList)
        Mockito.`when`(mainViewModel.informationList.value!!.size).thenReturn(100)
    }

    /**
     * Check for same actual and expected value.
     */
    @Test
    fun getTitle_success() {
        assertEquals(expectedTitle,this.mainViewModel.title.value)
    }

    /**
     * Check for different actual and expected value.
     */
    @Test
    fun getTitle_failure() {
        expectedTitle = "Canada1"
        assertFalse("getTitle_failure",mainViewModel.title.value==expectedTitle)
    }

    /**
     * Check for same expected and actual size of array list
     */
    @Test
    fun getInformationList_success(){
        assertEquals(expectedSize,mainViewModel.informationList.value!!.size)

    }

    /**
     * Check for different expected and actual size of array list.
     */

    @Test
    fun getInformationList_failure(){
        expectedSize = 1001
        assertFalse("getInformationList_failure ",mainViewModel.informationList.value!!.size==expectedSize)

    }
}