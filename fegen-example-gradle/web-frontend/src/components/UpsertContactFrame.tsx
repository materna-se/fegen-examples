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
import React, {Component} from "react";
import {observer} from "mobx-react";
import newContactModel from "../model/upsertContactModel";


@observer
export default class UpsertContactFrame extends Component {

    render() {
        return <div>
            <h2>Upsert Contact</h2>
            <div>
                <TextField
                    label={"First Name"}
                    value={newContactModel.contact.firstName}
                    onEdit={newContactModel.setContactField("firstName")}
                />
                <TextField
                    label={"Last Name"}
                    value={newContactModel.contact.lastName}
                    onEdit={newContactModel.setContactField("lastName")}
                />
                <TextField
                    label={"Number"}
                    value={newContactModel.contact.number || ""}
                    onEdit={newContactModel.setContactField("number")}
                />
                <TextField
                    label={"Street"}
                    value={newContactModel.contact.address?.street || ""}
                    onEdit={newContactModel.setAddressField("street")}
                />
                <TextField
                    label={"ZIP"}
                    value={newContactModel.contact.address?.zip || ""}
                    onEdit={newContactModel.setAddressField("zip")}
                />
                <TextField
                    label={"City"}
                    value={newContactModel.contact.address?.city || ""}
                    onEdit={newContactModel.setAddressField("city")}
                />
                <TextField
                    label={"Country"}
                    value={newContactModel.contact.address?.country || ""}
                    onEdit={newContactModel.setAddressField("country")}
                />
                <input
                    type="button"
                    value="Save"
                    onClick={() => newContactModel.save()}
                />
            </div>
        </div>
    }
}

class TextField extends Component<{label: string, value: string, onEdit: (value: string) => void}> {

    render() {
        return <div>
            <span>{this.props.label}</span>
            <input
                type="text"
                value={this.props.value}
                onChange={e => this.props.onEdit(e.target.value)}
            />
        </div>
    }
}

