import dash
import json
import pandas as pd
import plotly.graph_objects as go
import pymysql
from dash import dcc, html
from plotly.subplots import make_subplots

# Database connection details
DB_CONFIG = {
    "host": "localhost",  # Replace with your host
    "user": "root",  # Replace with your username
    "password": "root123",  # Replace with your password
    "database": "marsdatabase",  # Replace with your database name
}

# Query to fetch data from MySQL table
QUERY = "SELECT * FROM data_20241108_161641;"  # Replace with your table name


# Connect to the database and fetch data
def fetch_data():
    connection = pymysql.connect(**DB_CONFIG)
    try:
        df = pd.read_sql(QUERY, connection)
    finally:
        connection.close()
    return df


# Load and process data
data_df = fetch_data()
data_df["PRE"] = data_df["PRE"].apply(json.loads)
data_df["AT"] = data_df["AT"].apply(json.loads)
data_df["HWS"] = data_df["HWS"].apply(json.loads)
data_df["WD"] = data_df["WD"].apply(json.loads)

# Extract data for visualization
pressure_avg = [entry["av"] for entry in data_df["PRE"]]
pressure_min = [entry["mn"] for entry in data_df["PRE"]]
pressure_max = [entry["mx"] for entry in data_df["PRE"]]
temperature_avg = [entry["av"] for entry in data_df["AT"]]
temperature_min = [entry["mn"] for entry in data_df["AT"]]
temperature_max = [entry["mx"] for entry in data_df["AT"]]
wind_speed_avg = [entry["av"] for entry in data_df["HWS"]]
wind_speed_min = [entry["mn"] for entry in data_df["HWS"]]
wind_speed_max = [entry["mx"] for entry in data_df["HWS"]]

# Process wind direction data
wind_direction_counts = {}
for wd in data_df["WD"]:
    for key, value in wd.items():
        if key.isdigit():
            compass_point = value["compass_point"]
            count = value["ct"]
            wind_direction_counts[compass_point] = wind_direction_counts.get(compass_point, 0) + count

# Custom color schemes
CUSTOM_COLORS = {
    'background': '#1a1a1a',
    'card_background': '#2d2d2d',
    'text': '#ffffff',
    'pressure': '#00bfff',
    'temperature': '#ff4d4d',
    'wind': '#00ff99',
    'accent1': '#ffd700',
    'accent2': '#ff69b4'
}

# Initialize Dash app with custom styling
app = dash.Dash(__name__)

# Custom CSS
app.layout = html.Div([
    # Header Section
    html.Div([
        html.H1("Mars Weather Analytics Dashboard",
                style={'textAlign': 'center', 'color': CUSTOM_COLORS['text'], 'fontFamily': 'Space Mono, monospace',
                       'marginBottom': '20px'}),
        html.P("Interactive visualization of weather conditions on Mars",
               style={'textAlign': 'center', 'color': CUSTOM_COLORS['text'], 'fontFamily': 'Arial, sans-serif',
                      'fontSize': '1.2em'})
    ], style={'padding': '20px', 'backgroundColor': CUSTOM_COLORS['card_background'], 'borderRadius': '10px',
              'margin': '10px'}),

    # Main Dashboard Grid
    html.Div([
        # Left Column
        html.Div([
            # Weather Trends Panel
            html.Div([
                dcc.Graph(
                    id='weather-trends',
                    figure=make_subplots(
                        rows=3,
                        cols=1,
                        shared_xaxes=True,
                        subplot_titles=('Pressure Variation', 'Temperature Profile', 'Wind Speed Analysis'),
                        vertical_spacing=0.08
                    ).add_trace(
                        go.Scatter(
                            x=data_df['id'],
                            y=pressure_avg,
                            mode='lines+markers',
                            name='Pressure (Avg)',
                            line=dict(color=CUSTOM_COLORS['pressure'], width=2)
                        ),
                        row=1,
                        col=1
                    ).add_trace(
                        go.Scatter(
                            x=data_df['id'],
                            y=pressure_min,
                            mode='lines',
                            name='Pressure (Min)',
                            line=dict(color=CUSTOM_COLORS['pressure'], width=1, dash='dot')
                        ),
                        row=1,
                        col=1
                    ).add_trace(
                        go.Scatter(
                            x=data_df['id'],
                            y=pressure_max,
                            mode='lines',
                            name='Pressure (Max)',
                            fill='tonexty',
                            fillcolor='rgba(0,191,255,0.1)',
                            line=dict(color=CUSTOM_COLORS['pressure'], width=1, dash='dot')
                        ),
                        row=1,
                        col=1
                    ).add_trace(
                        go.Scatter(
                            x=data_df['id'],
                            y=temperature_avg,
                            mode='lines+markers',
                            name='Temperature (Avg)',
                            line=dict(color=CUSTOM_COLORS['temperature'], width=2)
                        ),
                        row=2,
                        col=1
                    ).add_trace(
                        go.Scatter(
                            x=data_df['id'],
                            y=temperature_min,
                            mode='lines',
                            name='Temperature (Min)',
                            line=dict(color=CUSTOM_COLORS['temperature'], width=1, dash='dot')
                        ),
                        row=2,
                        col=1
                    ).add_trace(
                        go.Scatter(
                            x=data_df['id'],
                            y=temperature_max,
                            mode='lines',
                            name='Temperature (Max)',
                            fill='tonexty',
                            fillcolor='rgba(255,77,77,0.1)',
                            line=dict(color=CUSTOM_COLORS['temperature'], width=1, dash='dot')
                        ),
                        row=2,
                        col=1
                    ).add_trace(
                        go.Scatter(
                            x=data_df['id'],
                            y=wind_speed_avg,
                            mode='lines+markers',
                            name='Wind Speed (Avg)',
                            line=dict(color=CUSTOM_COLORS['wind'], width=2)
                        ),
                        row=3,
                        col=1
                    ).add_trace(
                        go.Scatter(
                            x=data_df['id'],
                            y=wind_speed_min,
                            mode='lines',
                            name='Wind Speed (Min)',
                            line=dict(color=CUSTOM_COLORS['wind'], width=1, dash='dot')
                        ),
                        row=3,
                        col=1
                    ).add_trace(
                        go.Scatter(
                            x=data_df['id'],
                            y=wind_speed_max,
                            mode='lines',
                            name='Wind Speed (Max)',
                            fill='tonexty',
                            fillcolor='rgba(0,255,153,0.1)',
                            line=dict(color=CUSTOM_COLORS['wind'], width=1, dash='dot')
                        ),
                        row=3,
                        col=1
                    ).update_layout(
                        template='plotly_dark',
                        plot_bgcolor=CUSTOM_COLORS['background'],
                        paper_bgcolor=CUSTOM_COLORS['card_background'],
                        font=dict(family='Space Mono, monospace', size=12, color=CUSTOM_COLORS['text']),
                        showlegend=True,
                        legend=dict(
                            orientation='h',
                            yanchor='bottom',
                            y=1.02,
                            xanchor='right',
                            x=1
                        ),
                        margin=dict(l=60, r=30, t=100, b=60),
                        height=800
                    )
                )
            ], style={'backgroundColor': CUSTOM_COLORS['card_background'], 'borderRadius': '10px', 'padding': '15px',
                      'margin': '10px'})
        ], style={'width': '70%', 'display': 'inline-block', 'verticalAlign': 'top'}),

        # Right Column
        html.Div([
            # Wind Direction Rose
            html.Div([
                dcc.Graph(
                    id='wind-direction-rose',
                    figure=go.Figure(
                        go.Barpolar(
                            r=list(wind_direction_counts.values()),
                            theta=list(wind_direction_counts.keys()),
                            marker_color=list(range(len(wind_direction_counts))),
                            marker_colorscale='Viridis',
                            opacity=0.8
                        )
                    ).update_layout(
                        template='plotly_dark',
                        plot_bgcolor=CUSTOM_COLORS['background'],
                        paper_bgcolor=CUSTOM_COLORS['card_background'],
                        font=dict(family='Space Mono, monospace', size=12, color=CUSTOM_COLORS['text']),
                        title='Wind Direction Distribution',
                        polar=dict(
                            radialaxis=dict(showticklabels=True, ticks=''),
                            angularaxis=dict(direction='clockwise', period=8)
                        ),
                        height=400
                    )
                )
            ], style={'backgroundColor': CUSTOM_COLORS['card_background'], 'borderRadius': '10px', 'padding': '15px',
                      'margin': '10px'}),

            # Temperature vs Pressure Correlation
            html.Div([
                dcc.Graph(
                    id='temperature-pressure-correlation',
                    figure=go.Figure(
                        go.Scatter(
                            x=temperature_avg,
                            y=pressure_avg,
                            mode='markers',
                            marker=dict(
                                size=10,
                                color=wind_speed_avg,
                                colorscale='Viridis',
                                showscale=True,
                                colorbar=dict(title='Wind Speed (m/s)'),
                                line=dict(color='white', width=1)
                            ),
                            text=[f'Record {i}' for i in data_df['id']],
                            hovertemplate='Temperature: %{x:.1f}°C<br>Pressure: %{y:.0f}Pa<br>%{text}<extra></extra>'
                        )
                    ).update_layout(
                        template='plotly_dark',
                        plot_bgcolor=CUSTOM_COLORS['background'],
                        paper_bgcolor=CUSTOM_COLORS['card_background'],
                        font=dict(family='Space Mono, monospace', size=12, color=CUSTOM_COLORS['text']),
                        title='Temperature-Pressure Correlation',
                        xaxis_title='Temperature (°C)',
                        yaxis_title='Pressure (Pa)',
                        height=400
                    )
                )
            ], style={'backgroundColor': CUSTOM_COLORS['card_background'], 'borderRadius': '10px', 'padding': '15px',
                      'margin': '10px'})
        ], style={'width': '30%', 'display': 'inline-block', 'verticalAlign': 'top'})
    ], style={'backgroundColor': CUSTOM_COLORS['background'], 'padding': '20px'})
], style={'backgroundColor': CUSTOM_COLORS['background'], 'minHeight': '100vh', 'padding': '20px'})

if __name__ == '__main__':
    app.run_server(debug=True)
