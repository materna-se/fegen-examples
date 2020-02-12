import React from 'react';
import ContactDetailsFrame from "./components/ContactDetailsFrame";
import UserFrame from "./components/UserFrame";
import UserContactsFrame from "./components/UserContactsFrame";
import ContactSearchFrame from "./components/ContactSearchFrame";
import RegexSearchFrame from "./components/RegexSearchFrame";
import UpsertContactFrame from "./components/UpsertContactFrame";

const App: React.FC = () => {

    return (
        <div className="app">
            <div className="mainContainer">
                <UserFrame/>
                <UserContactsFrame/>
                <ContactDetailsFrame/>
                <UpsertContactFrame/>
                <ContactSearchFrame/>
                <RegexSearchFrame/>
            </div>
        </div>
    );
};

export default App;
