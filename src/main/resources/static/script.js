const API = "/api";


// ======================================================
// SECTION NAVIGATION
// ======================================================

function showSection(sectionId) {

    document.querySelectorAll(".page")
        .forEach(page => page.classList.remove("active"));

    document.getElementById(sectionId)
        .classList.add("active");


    if (sectionId === "teams") {
        loadTeams();
    }

    if (sectionId === "fixtures") {
        loadFixtures();
    }

    if (sectionId === "matches") {
        loadFixtureOptions();
        loadMatches();
    }

    if (sectionId === "standings") {
        loadStandings();
    }
}


// ======================================================
// COMMON ERROR HANDLING
// ======================================================

async function getErrorMessage(response) {

    const text = await response.text();

    return text || "Something went wrong";
}


function showMessage(elementId, message, success) {

    const element = document.getElementById(elementId);

    element.textContent = message;

    element.className =
        success ? "message success" : "message error";
}


// ======================================================
// TEAM
// ======================================================

async function addTeam() {

    const teamName =
        document.getElementById("teamName").value.trim();

    if (!teamName) {

        showMessage(
            "teamMessage",
            "Please enter a team name",
            false
        );

        return;
    }


    try {

        const response = await fetch(`${API}/teams`, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                teamName: teamName
            })
        });


        if (!response.ok) {

            const error = await getErrorMessage(response);

            showMessage(
                "teamMessage",
                error,
                false
            );

            return;
        }


        const team = await response.json();


        showMessage(
            "teamMessage",
            `Team "${team.teamName}" registered successfully`,
            true
        );


        document.getElementById("teamName").value = "";

        loadTeams();

    }
    catch (error) {

        showMessage(
            "teamMessage",
            "Unable to connect to server",
            false
        );
    }
}


async function loadTeams() {

    try {

        const response =
            await fetch(`${API}/teams`);

        const teams =
            await response.json();

        const table =
            document.getElementById("teamTable");

        table.innerHTML = "";


        teams.forEach(team => {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${team.teamId}</td>
                <td>${team.teamName}</td>

                <td>
                    <button
                        class="delete-btn"
                        onclick="deleteTeam(${team.teamId})">
                        Delete
                    </button>
                </td>
            `;

            table.appendChild(row);
        });

    }
    catch (error) {

        console.error(error);
    }
}


async function deleteTeam(id) {

    const confirmDelete =
        confirm("Are you sure you want to delete this team?");

    if (!confirmDelete) {
        return;
    }


    try {

        const response =
            await fetch(`${API}/teams/${id}`, {
                method: "DELETE"
            });


        if (!response.ok) {

            const error =
                await getErrorMessage(response);

            alert(error);

            return;
        }


        alert("Team deleted successfully");

        loadTeams();

    }
    catch (error) {

        alert("Unable to connect to server");
    }
}


// ======================================================
// FIXTURES
// ======================================================

async function generateFixtures() {

    try {

        const response =
            await fetch(`${API}/fixtures/generate`, {
                method: "POST"
            });


        if (!response.ok) {

            const error =
                await getErrorMessage(response);

            showMessage(
                "fixtureMessage",
                error,
                false
            );

            return;
        }


        const fixtures =
            await response.json();


        showMessage(
            "fixtureMessage",
            `${fixtures.length} fixtures generated successfully`,
            true
        );


        loadFixtures();

    }
    catch (error) {

        showMessage(
            "fixtureMessage",
            "Unable to connect to server",
            false
        );
    }
}


async function loadFixtures() {

    try {

        const response =
            await fetch(`${API}/fixtures`);

        const fixtures =
            await response.json();

        const table =
            document.getElementById("fixtureTable");

        table.innerHTML = "";


        fixtures.forEach(fixture => {

            const row =
                document.createElement("tr");

            row.innerHTML = `
                <td>${fixture.fixtureId}</td>

                <td>${fixture.roundNumber}</td>

                <td>${fixture.team1.teamName}</td>

                <td>${fixture.team2.teamName}</td>
            `;

            table.appendChild(row);
        });

    }
    catch (error) {

        console.error(error);
    }
}


// ======================================================
// LOAD FIXTURES INTO MATCH DROPDOWN
// ======================================================

async function loadFixtureOptions() {

    try {

        const response =
            await fetch(`${API}/fixtures`);

        const fixtures =
            await response.json();

        const select =
            document.getElementById("fixtureSelect");


        select.innerHTML =
            `<option value="">Select Fixture</option>`;


        fixtures.forEach(fixture => {

            const option =
                document.createElement("option");

            option.value =
                fixture.fixtureId;

            option.textContent =
                `${fixture.team1.teamName} vs ${fixture.team2.teamName}`;

            select.appendChild(option);
        });

    }
    catch (error) {

        console.error(error);
    }
}


// ======================================================
// MATCH RESULT
// ======================================================

async function recordResult() {

    const fixtureId =
        document.getElementById("fixtureSelect").value;

    const team1Score =
        document.getElementById("team1Score").value;

    const team2Score =
        document.getElementById("team2Score").value;


    if (!fixtureId ||
        team1Score === "" ||
        team2Score === "") {

        showMessage(
            "matchMessage",
            "Please select a fixture and enter both scores",
            false
        );

        return;
    }


    try {

        const response =
            await fetch(
                `${API}/matches/fixture/${fixtureId}/result`,
                {

                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify({

                        team1Score:
                            Number(team1Score),

                        team2Score:
                            Number(team2Score)
                    })
                }
            );


        if (!response.ok) {

            const error =
                await getErrorMessage(response);

            showMessage(
                "matchMessage",
                error,
                false
            );

            return;
        }


        await response.json();


        showMessage(
            "matchMessage",
            "Match result recorded successfully",
            true
        );


        document.getElementById("team1Score").value = "";
        document.getElementById("team2Score").value = "";
        document.getElementById("fixtureSelect").value = "";


        loadMatches();

    }
    catch (error) {

        showMessage(
            "matchMessage",
            "Unable to connect to server",
            false
        );
    }
}


async function loadMatches() {

    try {

        const response =
            await fetch(`${API}/matches`);

        const matches =
            await response.json();

        const table =
            document.getElementById("matchTable");

        table.innerHTML = "";


        matches.forEach(match => {

            const winner =
                match.winner
                    ? match.winner.teamName
                    : "Draw";


            const row =
                document.createElement("tr");


            row.innerHTML = `
                <td>${match.matchId}</td>

                <td>
                    ${match.fixture.team1.teamName}
                    vs
                    ${match.fixture.team2.teamName}
                </td>

                <td>
                    ${match.team1Score}
                    -
                    ${match.team2Score}
                </td>

                <td>${winner}</td>
            `;


            table.appendChild(row);
        });

    }
    catch (error) {

        console.error(error);
    }
}


// ======================================================
// STANDINGS
// ======================================================

async function loadStandings() {

    try {

        const response =
            await fetch(`${API}/standings`);

        const standings =
            await response.json();

        const table =
            document.getElementById("standingsTable");

        table.innerHTML = "";


        standings.forEach((standing, index) => {

            const row =
                document.createElement("tr");


            row.innerHTML = `
                <td>${index + 1}</td>

                <td>
                    ${standing.team.teamName}
                </td>

                <td>${standing.played}</td>

                <td>${standing.wins}</td>

                <td>${standing.draws}</td>

                <td>${standing.losses}</td>

                <td>
                    <strong>
                        ${standing.points}
                    </strong>
                </td>
            `;


            table.appendChild(row);
        });

    }
    catch (error) {

        console.error(error);
    }
}


// ======================================================
// INITIAL PAGE LOAD
// ======================================================

document.addEventListener(
    "DOMContentLoaded",
    function () {

        loadTeams();

    }
);