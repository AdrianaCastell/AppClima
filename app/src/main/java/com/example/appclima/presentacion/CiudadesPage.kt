
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appclima.repository.ApiRepositorio
import com.example.appclima.router.Enrutador
import com.example.appclima.presentacion.CiudadesView


@Composable
fun CiudadesPage(
    navHostController:  NavHostController
) {
    val viewModel : CiudadesViewModel = viewModel(
        factory = CiudadesViewModelFactory(
            repositorio = ApiRepositorio(),
            router = Enrutador(navHostController)
        )
    )
    CiudadesView(
        state = viewModel.uiState,
        onAction = { intencion ->
            viewModel.ejecutar(intencion)
        }
    )
}


