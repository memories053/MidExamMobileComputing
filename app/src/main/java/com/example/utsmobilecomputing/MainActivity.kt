package com.example.utsmobilecomputing

import android.os.Bundle
import android.provider.CalendarContract
import android.text.Layout
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.utsmobilecomputing.ui.theme.UTSMobileComputingTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTSMobileComputingTheme {
                Navigation()
//                EditScreen()
            }
        }
    }
}

data class Book (
    val judul: String,
    val pengarang: String,
    val penerbit: String,
    val tahunterbit: String,
    val rangkuman: String,
    val genre: String,
)

class TempData : ViewModel() {
    var bookList = mutableStateListOf<Book>()
        private set

    fun addBook(judul: String, pengarang: String, penerbit: String, tahunterbit: String, rangkuman: String, genre: String) {
        bookList.add(Book(judul, pengarang, penerbit, tahunterbit, rangkuman, genre))
    }

    fun updateBook(index: Int, newJudul: String, newPengarang: String, newPenerbit: String, newTahunTerbit: String, newRangkuman: String, newGenre: String) {
        if (index in bookList.indices) {
            bookList[index] = Book(newJudul, newPengarang, newPenerbit, newTahunTerbit, newRangkuman, newGenre)
        }
    }

    var tempJudul = mutableStateOf("")
        private set

    var tempPengarang = mutableStateOf("")
        private set

    var tempPenerbit = mutableStateOf("")
        private set

    var tempTahunTerbit = mutableStateOf("")
        private set

    var tempRangkuman = mutableStateOf("")
        private set

    var isSaved = mutableStateOf(false)
        private set

    fun updateData(newJudul: String, newPengarang: String, newPenerbit: String, newTahunTerbit: String, newRangkuman: String) {
        tempJudul.value = newJudul
        tempPengarang.value = newPengarang
        tempPenerbit.value = newPenerbit
        tempTahunTerbit.value = newTahunTerbit
        tempRangkuman.value = newRangkuman
    }

    fun saveData() {
        isSaved.value = true
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val tempData = TempData()

    NavHost(navController = navController, startDestination = "home"){
        composable("home"){ HomeScreen(navController, tempData)}
        composable("edit/{index}"){ backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toInt() ?: 0
            EditScreen(navController, tempData, index)
        }
        composable("tambah"){ TambahScreen(navController, tempData)}
        composable("detail/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toInt() ?: 0
            DetailScreen(navController, tempData, index)
        }
    }
}

@Composable
fun HomeScreen(
    navController: androidx.navigation.NavController,
    tempData: TempData
){
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("tambah")}) {
                Text("Tambah \n Buku", modifier = Modifier.padding(all = 10.dp))
            }
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(all = 30.dp)
        ) {
            Text("Daftar Buku", fontSize = 25.sp, modifier = Modifier.padding(vertical = 10.dp))

            for ((index, book) in tempData.bookList.withIndex()){
                Box(
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .padding(all = 10.dp)
                        .clickable { navController.navigate("detail/$index") }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Judul : ${book.judul}");
                            Text("Pengarang : ${book.pengarang}")
                        }
                        Box(
                            modifier = Modifier
                                .background(color = Color.Gray)
                                .padding(all = 10.dp)
                                .clickable { navController.navigate("edit/$index") }
                        ) {
                            Text("Edit")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

//            Spacer(modifier = Modifier.height(20.dp))
//
//            Box(
//                modifier = Modifier
//                    .background(color = Color.LightGray)
//                    .padding(all = 10.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Column {
//                        Text("Judul :");
//                        Text("Pengarang :")
//                    }
//                    Box(
//                        modifier = Modifier
//                            .background(color = Color.Gray)
//                            .padding(all = 10.dp)
//                    ) {
//                        Text("Edit")
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Box(
//                modifier = Modifier
//                    .background(color = Color.LightGray)
//                    .padding(all = 10.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Column {
//                        Text("Judul :");
//                        Text("Pengarang :")
//                    }
//                    Box(
//                        modifier = Modifier
//                            .background(color = Color.Gray)
//                            .padding(all = 10.dp)
//                    ) {
//                        Text("Edit")
//                    }
//                }
//            }
        }
    }
}

@Composable
fun EditScreen(
    navController: androidx.navigation.NavController,
    tempData: TempData,
    bookIndex: Int,
    textValue: String = "",
    onTextChange: (String) -> Unit = {},
) {
    val book = tempData.bookList[bookIndex]
    var textFieldValueJudul by remember { mutableStateOf(TextFieldValue(tempData.tempJudul.value)) }
    var textFieldValuePengarang by remember { mutableStateOf(TextFieldValue(tempData.tempPengarang.value)) }
    var textFieldValuePenerbit by remember { mutableStateOf(TextFieldValue(tempData.tempPenerbit.value)) }
    var textFieldValueTahunTerbit by remember { mutableStateOf(TextFieldValue(tempData.tempTahunTerbit.value)) }
    var textFieldValueRangkuman by remember { mutableStateOf(TextFieldValue(tempData.tempRangkuman.value)) }

    var selectedGenre by remember { mutableStateOf(book.genre) }
    val genres = listOf("Misteri", "Fantasi", "Romansa")

    var isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(0)
    }

    val usernames = listOf("Misteri", "Romansa", "Fantasi")

    Column (
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 50.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Judul", fontSize = 30.sp)
            OutlinedTextField(
                value = textFieldValueJudul,
                onValueChange = { newValue ->
                    textFieldValueJudul = newValue
                    onTextChange(newValue.text)
                },
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Pengarang", fontSize = 30.sp)
            OutlinedTextField(
                value = textFieldValuePengarang,
                onValueChange = { newValue ->
                    textFieldValuePengarang = newValue
                    onTextChange(newValue.text)
                },
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Penerbit", fontSize = 30.sp)
            OutlinedTextField(
                value = textFieldValuePenerbit,
                onValueChange = { newValue ->
                    textFieldValuePenerbit = newValue
                    onTextChange(newValue.text)
                },
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Tahun Terbit", fontSize = 30.sp)
            OutlinedTextField(
                value = textFieldValueTahunTerbit,
                onValueChange = { newValue ->
                    textFieldValueTahunTerbit = newValue
                    onTextChange(newValue.text)
                },
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Genre", fontSize = 30.sp)
            Box (
                modifier = Modifier
                    .clickable { isDropDownExpanded.value = true }
                    .border(
                        BorderStroke(1.dp, Color.Black)
                    )
                    .padding(all = 10.dp)
            ){
                Row (
                    modifier = Modifier.width(130.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(text = usernames[itemPosition.value])
                    Image(
                        painter = painterResource(id = R.drawable.dropdown),
                        contentDescription = "drop down icon",
                        modifier = Modifier.size(10.dp)
                    )
                }
                DropdownMenu(
                    expanded = isDropDownExpanded.value,
                    onDismissRequest = {
                        isDropDownExpanded.value = false
                    }
                ) {
                    genres.forEach { genre ->
                        DropdownMenuItem( text = {
                            Text(text = genre)
                        },
                            onClick = {
                                selectedGenre = genre
                                isDropDownExpanded.value = false
                            })
                }}
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Rangkuman", fontSize = 30.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = textFieldValueRangkuman,
            onValueChange = { newValue ->
                textFieldValueRangkuman = newValue
                onTextChange(newValue.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                onClick = {
                    navController.navigate("home")
                }
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    tempData.updateBook(
                        bookIndex,
                        textFieldValueJudul.text,
                        textFieldValuePengarang.text,
                        textFieldValuePenerbit.text,
                        textFieldValueTahunTerbit.text,
                        textFieldValueRangkuman.text,
                        selectedGenre
                    )
                    navController.navigate("home")
                }
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
fun TambahScreen(
    navController: androidx.navigation.NavController,
    tempData: TempData,
    onTextChange: (String) -> Unit = {},
) {
    var textFieldValueJudul by remember { mutableStateOf(TextFieldValue("")) }
    var textFieldValuePengarang by remember { mutableStateOf(TextFieldValue("")) }
    var textFieldValuePenerbit by remember { mutableStateOf(TextFieldValue("")) }
    var textFieldValueTahunTerbit by remember { mutableStateOf(TextFieldValue("")) }
    var textFieldValueRangkuman by remember { mutableStateOf(TextFieldValue("")) }
    var selectedGenre by remember { mutableStateOf("Select Genre") }
    val genres = listOf("Misteri", "Fantasi", "Romansa")

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(0)
    }

    val usernames = listOf("Misteri", "Romansa", "Fantasi")

    Column (
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 50.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Judul", fontSize = 30.sp)
            OutlinedTextField(
                value = textFieldValueJudul,
                onValueChange = { newValue ->
                    textFieldValueJudul = newValue
                    onTextChange(newValue.text)
                },
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Pengarang", fontSize = 30.sp)
            OutlinedTextField(
                value = textFieldValuePengarang,
                onValueChange = { newValue ->
                    textFieldValuePengarang = newValue
                    onTextChange(newValue.text)
                },
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Penerbit", fontSize = 30.sp)
            OutlinedTextField(
                value = textFieldValuePenerbit,
                onValueChange = { newValue ->
                    textFieldValuePenerbit = newValue
                    onTextChange(newValue.text)
                },
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Tahun Terbit", fontSize = 30.sp)
            OutlinedTextField(
                value = textFieldValueTahunTerbit,
                onValueChange = { newValue ->
                    textFieldValueTahunTerbit = newValue
                    onTextChange(newValue.text)
                },
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text("Genre", fontSize = 30.sp)
            Box (
                modifier = Modifier
                    .clickable { isDropDownExpanded.value = true }
                    .border(
                        BorderStroke(1.dp, Color.Black)
                    )
                    .padding(all = 10.dp)
            ){
                Row (
                    modifier = Modifier.width(130.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(text = selectedGenre)
                    Image(
                        painter = painterResource(id = R.drawable.dropdown),
                        contentDescription = "drop down icon",
                        modifier = Modifier.size(10.dp)
                    )
                }
                DropdownMenu(
                    expanded = isDropDownExpanded.value,
                    onDismissRequest = {
                        isDropDownExpanded.value = false
                    }
                ) {
                    genres.forEach { genre ->
                        DropdownMenuItem(text = {
                            Text(text = genre)
                        },
                            onClick = {
                                selectedGenre = genre
                                isDropDownExpanded.value = false
                            })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Rangkuman", fontSize = 30.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = textFieldValueRangkuman,
            onValueChange = { newValue ->
                textFieldValueRangkuman = newValue
                onTextChange(newValue.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                onClick = {
                    navController.navigate("home")
                }
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    tempData.addBook(
                        textFieldValueJudul.text,
                        textFieldValuePengarang.text,
                        textFieldValuePenerbit.text,
                        textFieldValueTahunTerbit.text,
                        textFieldValueRangkuman.text,
                        selectedGenre
                    )
                    navController.navigate("home")
                }
            ) {
                Text("Tambah")
            }
        }
    }
}

@Composable
fun DetailScreen(
    navController: androidx.navigation.NavController,
    tempData: TempData,
    bookIndex: Int
) {
    val book = tempData.bookList[bookIndex]
    Column (
        modifier = Modifier.padding(all = 50.dp)
    ){
        Box() {
            Column {
                Text("Judul", fontSize = 30.sp)
                Text("${book.judul}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box() {
            Column {
                Text("Pengarang", fontSize = 30.sp)
                Text("${book.pengarang}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box() {
            Column {
                Text("Penerbit", fontSize = 30.sp)
                Text("${book.penerbit}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box() {
            Column {
                Text("Tahun Terbit", fontSize = 30.sp)
                Text("${book.tahunterbit}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box() {
            Column {
                Text("Genre", fontSize = 30.sp)
                Text("${book.genre}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box() {
            Column {
                Text("Rangkuman", fontSize = 30.sp)
                Text("${book.rangkuman}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {navController.navigate("home")}
        ) {
            Text("Back")
        }
    }

}