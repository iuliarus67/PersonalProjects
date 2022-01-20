import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddBookComponent } from './components/add-book/add-book.component';
import { BookListComponent } from './components/book-list/book-list.component';
import { CreateCustomerComponent } from './components/create-customer/create-customer.component';
import { CreateEmployeeComponent } from './components/create-employee/create-employee.component';
import { LoggedCustomerComponent } from './components/logged-customer/logged-customer.component';
import { LoggedEmployeeComponent } from './components/logged-employee/logged-employee.component';
import { LoginComponent } from './components/login/login.component';
import { ModifyPointsComponent } from './components/modify-points/modify-points.component';
import { ViewAccountComponent } from './components/view-account/view-account.component';
import { ViewCartComponent } from './components/view-cart/view-cart.component';
import { LoggedEmployeeService } from './services/logged-employee.service';

const routes: Routes = [
    {path: 'books', component: BookListComponent},
    {path: 'create-employee', component: CreateEmployeeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'customer-loggedIn/:string', component: LoggedCustomerComponent},
    {path: 'view-account/:string', component: ViewAccountComponent},
    {path: 'manage-customer/:string/:string2', component: CreateCustomerComponent},
    {path: 'employee-loggedIn/:string', component: LoggedEmployeeComponent},
    {path: 'modify-points/:string', component: ModifyPointsComponent},
    {path: 'add-book', component:AddBookComponent},
    {path: '', redirectTo: 'books', pathMatch: 'full'}
 ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],                                                                                                                                                                                                                                                                                                          
  exports: [RouterModule]
})
export class AppRoutingModule { }