package com.jonathanmojica.examenandroid.ui.view.login

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jonathanmojica.examenandroid.R
import com.jonathanmojica.examenandroid.databinding.FragmentLoginBinding
import com.jonathanmojica.examenandroid.service.ApiRepository
import com.jonathanmojica.examenandroid.utils.Settings
import com.jonathanmojica.examenandroid.utils.Status
import java.util.regex.Pattern


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val repository = Settings.getRetrofit()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val sharedPref = activity?.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
        val logueado = sharedPref!!.getBoolean("Logueado",false)
        if(logueado)
        {
            findNavController().navigate(R.id.action_loginFragment_to_mapaFragment)
        }
        initViewModel()
        init()
        responses()
        return binding.root
    }

    /**
     * Se inicializa el viewmodel
     */
    private fun initViewModel()
    {
        viewModel = ViewModelProvider(
            this,
            LoginFactory(ApiRepository(repository))
        ).get(LoginViewModel::class.java)
    }

    /**
     * Obtiene las respuestas del servidor
     */
    private fun responses()
    {
        viewModel.getLogin.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val name = getString(R.string.channel)
                            val descriptionText = getString(R.string.channel_desc)
                            val importance = NotificationManager.IMPORTANCE_DEFAULT
                            val channel = NotificationChannel(name, name, importance).apply {
                                description = descriptionText
                            }
                            // Register the channel with the system
                            var build = NotificationCompat.Builder(requireContext(), name).setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Exito")
                                .setContentText("Tu codigo es el siguiente: ${it.data?.code}")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                            val nm = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                            nm.createNotificationChannel(channel)
                            nm.notify(0,build.build())
                            val sharedPref = activity?.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
                            sharedPref!!.edit().putBoolean("Logueado",true).apply()

                        }
                        findNavController().navigate(R.id.action_loginFragment_to_mapaFragment)

                    }
                    else ->{
                        Toast.makeText(context, "Ha ocurrido un error mientras se intenta iniciar sesión", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }


    /**
     * Metodo generado para agregar listener
     */
    private fun init()
    {
        binding.btnIngresar.setOnClickListener {

            if(binding.txtEmail.text!!.isNotEmpty() && binding.txtPassword.text!!.isNotEmpty())
            {
                if(isValidEmail(binding.txtEmail.text.toString()))
                {
                   if(binding.txtEmail.text!!.length < 80)
                   {
                       if(binding.txtPassword.text!!.length < 8)
                       {
                           try{
                               var email = binding.txtEmail.text
                               var password = binding.txtPassword.text
                               Log.d("LoginFragment","Hola:$email - $password")
                               viewModel.login(email= email.toString(), password = password.toString())
                           }catch (e:Exception)
                           {
                               e.printStackTrace()
                           }

                       }else Toast.makeText(context, "La contraseña debe tener menos de 8 caracteres", Toast.LENGTH_SHORT).show()

                   }else Toast.makeText(context, "El correo debe tener menos de 80 caracteres", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "El correo no tiene el formato adecuado", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "Tienes algun campo sin llegar", Toast.LENGTH_SHORT).show()
            }

        }
    }


    /**
     * Valida el email
     */
    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}