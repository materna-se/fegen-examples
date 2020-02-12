/**
 * Copyright 2020 Materna Information & Communications SE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import {observer} from "mobx-react"
import React, {Component} from "react";
import {observable, runInAction} from "mobx";
import userModel from "../model/userModel";

@observer
export default class UserFrame extends Component {

    render() {
        return <div>
            <h2>User</h2>
            <UserSelector/>
            <UserCreator/>
        </div>
    }
}

@observer
class UserSelector extends Component {

    chooseUser(name: string) {
        userModel.selectUser(userModel.users.find(u => u.name === name))
    }

    render() {
        return <div>
            <div>
                <span>Current user: </span>
                <select value={userModel.loggedInUser?.name || "Logged out"}
                        onChange={e => this.chooseUser(e.target.value)}>
                    {userModel.users.map(user =>
                        <option key={user.id} onClick={() => userModel.selectUser(user)}>
                            {user.name}
                        </option>
                    )}
                    <option>Logged out</option>
                </select>
            </div>
        </div>
    }
}

@observer
class UserCreator extends Component {

    @observable newName: string = "";

    async createUser() {
        await userModel.createUser(this.newName);
        runInAction("Reset new user name", () => this.newName = "");
    }

    render() {
        return <span>
            <span>Create user: </span>
            <input type="text" value={this.newName}
                   onChange={e => runInAction("Change new user name", () => this.newName = e.target.value)}/>
            <input type="button" value="Create User" onClick={() => this.createUser()}/>
        </span>;
    }
}
