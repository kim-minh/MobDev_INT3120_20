package com.example.week10

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.week10.ui.theme.Week10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week10Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        val requestPermissionLauncher =
                            rememberLauncherForActivityResult(
                                ActivityResultContracts.RequestPermission()
                            ) { isGranted: Boolean ->
                                if (isGranted) {
                                    Log.i("Permission", "Permission granted")
                                } else {
                                    Log.i("Permission", "Permission denied")
                                }
                            }

                        LoadContactBtn(requestPermissionLauncher)
                        AddContactBtn{}
                        ContactList()
                    }
                }
            }
        }
    }
}

data class Contact(val id: String, val name: String, val number: String)


@Composable
fun retrieveContacts(): MutableList<Contact> {
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    // Define the columns you want to retrieve
    val projection = arrayOf(
        ContactsContract.CommonDataKinds.Phone._ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER
    )

    val cursor = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        projection,
        null,
        null,
        null
    )

    val contacts = mutableListOf<Contact>()

    cursor?.use { c ->
        val idColumn = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
        val nameColumn = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val numberColumn = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

        while (c.moveToNext()) {
            val id = c.getString(idColumn)
            val name = c.getString(nameColumn)
            val number = c.getString(numberColumn)
            contacts.add(Contact(id, name, number))
        }
    }

    // Now you have a list of contacts in the 'contacts' variable
    return contacts
}


@Composable
fun LoadContactBtn(launcher: ManagedActivityResultLauncher<String, Boolean>) {
    val context = LocalContext.current
    var granted by remember { mutableStateOf(false) }

    Button(onClick = {
        val permission = android.Manifest.permission.READ_CONTACTS
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            granted = true
        }
        else {
            launcher.launch(permission)
        }
    }) {
        Text("Load contact")
    }

    if (granted) {
        ContactList()
    }
}

@Composable
fun AddContactBtn(requestPermission: () -> Unit) {
    Button(onClick = requestPermission) {
        Text("Add contact")
    }
}

@Composable
fun ContactList() {
    val contacts = retrieveContacts()
    LazyColumn(contentPadding = PaddingValues(10.dp)) {
        items(contacts) { item ->
            Text(text = item.name)
            Divider()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Week10Theme {
        Column {
            //LoadContactBtn()
            AddContactBtn {}
            ContactList()
        }
    }
}