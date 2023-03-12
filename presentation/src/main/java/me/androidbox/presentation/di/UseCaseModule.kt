package me.androidbox.presentation.di


import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.androidbox.domain.authentication.usecase.*
import me.androidbox.domain.authentication.usecase.imp.*

@InstallIn(ViewModelComponent::class)
@Module
interface UseCaseModule {

    @Reusable
    @Binds
    fun bindsRetrieveTokenUseCaseImp(retrieveTokenUseCaseImp: RetrieveCurrentLoginUserUseCaseImp): RetrieveCurrentLoginUserUseCase

    @Reusable
    @Binds
    fun bindsSaveTokenUseCaseImp(saveTokenUseCaseImp: SaveTokenUseCaseImp): SaveTokenUseCase

    @Reusable
    @Binds
    fun bindsSaveUserIdUseCaseImp(saveUserIdUseCaseImp: SaveUserIdUseCaseImp): SaveUserIdUseCase

    @Reusable
    @Binds
    fun bindsSaveFullNameUseCaseImp(saveFullNameUseCaseImp: SaveFullNameUseCaseImp): SaveFullNameUseCase

    @Reusable
    @Binds
    fun bindsDeleteCurrentUserUseCaseImp(deleteCurrentUserUseCaseImp: DeleteCurrentUserUseCaseImp): DeleteCurrentUserUseCase

    @Reusable
    @Binds
    fun bindsRegisterUseCaseImp(registerUseCaseImp: RegisterUseCaseImp): RegisterUseCase

    @Reusable
    @Binds
    fun bindsLoginUseCaseImp(loginUseCaseImp: LoginUseCaseImp): LoginUseCase
}
